package cn.goblincwl.dragontwilight.common.result;

import cn.goblincwl.dragontwilight.common.exception.DtWebException;
import org.slf4j.Logger;

import java.util.List;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 统一接口返回格式 生成器
 * @create 2020-05-25 21:27
 */
public class ResultGenerator {

    protected static final String DEFAULT_SUCCESS_MESSAGE = "成功";
    protected static final String DEFAULT_NODATA_MESSAGE = "暂无数据";

    /**
     * 成功类型的返回
     *
     * @param data 返回时携带的数据
     * @return cn.goblincwl.dragontwilight.common.result.Result
     * @create 2020/5/25 21:52
     * @author ☪wl
     */
    public static Result genSuccessResult(Object data) {
        if (data == null)
            return new Result(DEFAULT_NODATA_MESSAGE, ResultCode.SUCCESS, null);
        if (data instanceof List) {
            if (((List) data).size() <= 0) {
                return new Result(DEFAULT_NODATA_MESSAGE, ResultCode.SUCCESS, null);
            }
        }
        return new Result(DEFAULT_SUCCESS_MESSAGE, ResultCode.SUCCESS, data);
    }

    /**
     * 失败类型的返回
     *
     * @param message 返回时携带的提示
     * @return cn.goblincwl.dragontwilight.common.result.Result
     * @create 2020/5/25 21:53
     * @author ☪wl
     */
    public static Result genFailResult(String message) {
        return new Result(message, ResultCode.FAIL, null);
    }

    /**
     * 返回错误封装
     *
     * @param defaultMsg 默认消息
     * @param log        日志对象
     * @param e          异常对象
     * @return cn.goblincwl.dragontwilight.common.result.Result
     * @create 2020/6/16 22:54
     * @author ☪wl
     * @document ShowDoc:
     */
    public static String autoReturnFailResult(String defaultMsg, Logger log, Exception e) {
        String resultMsg = (e instanceof DtWebException) ? e.getMessage() : defaultMsg;
        log.error(resultMsg, e);
        return ResultGenerator.genFailResult(resultMsg).toString();
    }
}
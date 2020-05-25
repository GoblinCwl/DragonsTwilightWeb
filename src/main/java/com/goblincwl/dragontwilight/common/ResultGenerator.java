package com.goblincwl.dragontwilight.common;

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
     * @return com.goblincwl.dragontwilight.common.Result
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
     * @return com.goblincwl.dragontwilight.common.Result
     * @create 2020/5/25 21:53
     * @author ☪wl
     */
    public static Result genFailResult(String message) {
        return new Result(message, ResultCode.FAIL, null);
    }
}
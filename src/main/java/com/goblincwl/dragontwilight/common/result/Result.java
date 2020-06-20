package com.goblincwl.dragontwilight.common.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 统一接口返回格式 返回类型
 * @create 2020-05-25 21:27
 */
@Data
@AllArgsConstructor
public class Result {
    private String message;
    private String code;
    private Object data;

    public Result() {
        super();
        this.code = "1";
        this.message = ResultGenerator.DEFAULT_SUCCESS_MESSAGE;
        this.data = null;
    }

    protected Result(String message, ResultCode resultCode, Object data) {
        this.message = message;
        this.code = resultCode.toString();
        this.data = data;
    }

    @Override
    public String toString() {
        SerializeFilter[] filters = new SerializeFilter[1];
        filterToString(filters);
        return JSON.toJSONString(this,
                SerializeConfig.globalInstance,
                filters,
                "yyyy-MM-dd",
                JSON.DEFAULT_GENERATE_FEATURE,
                SerializerFeature.WriteMapNullValue);
    }

    public String toStringHms() {
        SerializeFilter[] filters = new SerializeFilter[1];
        filterToString(filters);
        return JSON.toJSONString(this,
                SerializeConfig.globalInstance,
                filters, "yyyy-MM-dd HH:mm:ss",
                JSON.DEFAULT_GENERATE_FEATURE,
                SerializerFeature.WriteMapNullValue);

    }

    /**
     * 过滤数据
     *
     * @param filters ValueFilter
     * @return void
     * @create 2020/5/25 21:40
     * @author ☪wl
     */
    private void filterToString(SerializeFilter[] filters) {
        ValueFilter filter = (object, name, value) -> {
            if (value == null && !"data".equals(name))
                return "";
            return value;
        };
        filters[0] = filter;
    }
}

/**
 * @author ☪wl
 * @program dragons-twilight-web
 * @description 统一接口返回格式 状态码枚举
 * @create 2020-05-25 21:27
 */
@AllArgsConstructor
enum ResultCode {
    SUCCESS("1"),
    FAIL("0");
    protected String code;

    @Override
    public String toString() {
        return code;
    }
}
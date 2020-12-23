package com.hejun.colud.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hejun.colud.common.constants.ResultCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * 返回结果封装类
 *
 * @author HeJun
 */
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> implements Serializable {

    private Integer code;
    private String msg;
    private T data;

    public static <T> Result SUCCESS(T data) {
        Result result = new Result();
        result.code = ResultCode.OK.getCode();
        result.msg = ResultCode.OK.getDesc();
        result.data = data;
        return result;
    }

    public static <T> Result SUCCESS() {
        return Result.SUCCESS(null);
    }

    public static <T> Result ERROR(ResultCode code, String msg, T data) {
        Result result = new Result();
        result.code = code.getCode();
        result.msg = msg;
        result.data = data;
        return result;
    }

    public static <T> Result ERROR(ResultCode code, String msg) {
        return Result.ERROR(code, msg, null);
    }

    public static <T> Result ERROR(ResultCode code) {
        return Result.ERROR(code, code.getDesc(), null);
    }
}

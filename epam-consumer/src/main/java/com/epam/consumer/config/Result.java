package com.epam.consumer.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther: chenmingyu
 * @date: 2018/10/31 16:47
 * @description:
 */
@Data
public class Result<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    private Result() {
    }

    private Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private Result(int code, String msg, T data) {
        this(code, data);
        this.msg = msg;
    }

    public static Result success() {
        return new Result();
    }

    public static <T> Result<T> success(T data) {
        return new Result(0, data);
    }

    public static <T> Result<T> error(int code, String msg, T data) {
        return new Result(code, msg, data);
    }

}

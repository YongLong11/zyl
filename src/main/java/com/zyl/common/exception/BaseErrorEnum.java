package com.zyl.common.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum BaseErrorEnum implements ErrorInfo{
    DEFAULT(50000, "服务器错误"),

    THIRD_SERVICE_ERROR(40001, "请求第三方错误");

    final int code;
    final String msg;

    public int getCode(){
        return this.code;
    }

    public String getMsg(){
        return this.msg;
    }
}

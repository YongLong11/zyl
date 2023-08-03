package com.ziroom.zyl.common.exception;


import java.text.MessageFormat;

public class BusinessException extends RuntimeException {
    private final int code;
    private final String msg;

    public BusinessException(String message){
        this(BaseErrorEnum.DEFAULT.getCode(), message);
    }
    public BusinessException(int code, String message){
        super(message);
        this.code = code;
        this.msg = message;
    }

    public BusinessException(ErrorInfo errorInfo, Object... item) {
        this(errorInfo.getCode(), MessageFormat.format(errorInfo.getMsg(), item));
    }
    public int getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

}

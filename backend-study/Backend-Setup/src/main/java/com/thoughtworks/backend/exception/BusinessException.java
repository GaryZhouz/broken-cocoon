package com.thoughtworks.backend.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDesc());
        this.errorCode = errorCode;
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

    public BusinessException(ErrorCode errorCode, String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
        this.errorCode = errorCode;
    }

    public BusinessException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public BusinessException(ErrorCode errorCode, String arg0, Throwable arg1) {
        super(arg0, arg1);
        this.errorCode = errorCode;
    }

    public BusinessException(String arg0) {
        super(arg0);
    }

    public BusinessException(ErrorCode errorCode, String arg0) {
        super(arg0);
        this.errorCode = errorCode;
    }

    public BusinessException(Throwable arg0) {
        super(arg0);
    }

    public BusinessException(ErrorCode errorCode, Throwable arg0) {
        super(arg0);
        this.errorCode = errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

}

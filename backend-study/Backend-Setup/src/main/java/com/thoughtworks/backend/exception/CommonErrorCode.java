package com.thoughtworks.backend.exception;

public enum CommonErrorCode implements ErrorCode {

    NOT_FOUND(404, "Not Found"),

    UNKNOWN(999, "未知错误");

    private final int code;

    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    CommonErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static CommonErrorCode setErrorCode(int code) {
        for (CommonErrorCode errorCode : CommonErrorCode.values()) {
            if (errorCode.getCode() == code) {
                return errorCode;
            }
        }
        return null;
    }
}

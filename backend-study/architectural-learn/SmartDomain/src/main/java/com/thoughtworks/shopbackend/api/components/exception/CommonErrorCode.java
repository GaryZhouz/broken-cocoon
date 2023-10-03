package com.thoughtworks.shopbackend.api.components.exception;

public enum CommonErrorCode implements ErrorCode {

    CONTAINER_NOT_PAID_ORDER(101, "该用户存在一笔待支付订单"),
    CREATE_ORDER_FAILED(102, "订单创建失败"),

    USER_NOT_EXIST(201, "用户不存在"),

    COMMODITY_NOT_FOUND(404, "商品资源不存在"),

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

package com.xupt.xiyoumobile.common;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-26 20:30
 */
public enum ApiRspCode {

    SUCCESS(1, "SUCCESS"),
    ERROR(0, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"),
    ALREADY_SOLD(3, "ALREADY_SOLD");

    private final int code;
    private final String desc;

    ApiRspCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}

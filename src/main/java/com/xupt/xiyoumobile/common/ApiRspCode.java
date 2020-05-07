package com.xupt.xiyoumobile.common;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-26 20:30
 */
public enum ApiRspCode {

    SUCCESS(1, "SUCCESS"),
    ERROR(0, "ERROR"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"),
    USER_NOTFOUND(3, "USER_NOTFOUND"),
    WRONG_PWD(4, "WRONG_PWD"),
    UNAUTHORIZED(10, "UNAUTHORIZED"),
    FORBIDDEN(11, "FORBIDDEN"),
    DB_ERROR(12, "DB_ERROR");

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

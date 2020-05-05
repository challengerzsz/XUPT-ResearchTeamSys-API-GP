package com.xupt.xiyoumobile.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-26 20:31
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ApiResponse<T> {

    private int status;
    private String msg;
    private T data;

    private ApiResponse(int status) {
        this.status = status;
    }

    private ApiResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ApiResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ApiResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ApiRspCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ApiResponse<T> createBySuccess() {
        return new ApiResponse<T>(ApiRspCode.SUCCESS.getCode());
    }

    public static <T> ApiResponse<T> createBySuccessMsg(String msg) {
        return new ApiResponse<>(ApiRspCode.SUCCESS.getCode(), msg);
    }

    public static <T> ApiResponse<T> createBySuccess(T data) {
        return new ApiResponse<>(ApiRspCode.SUCCESS.getCode(), data);
    }

    public static <T> ApiResponse<T> createBySuccess(String msg, T data) {
        return new ApiResponse<>(ApiRspCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> ApiResponse<T> createByError() {
        return new ApiResponse<>(ApiRspCode.ERROR.getCode(), ApiRspCode.ERROR.getDesc());
    }

    public static <T> ApiResponse<T> createByErrorMsg(String errorMsg) {
        return new ApiResponse<>(ApiRspCode.ERROR.getCode(), errorMsg);
    }

    public static <T> ApiResponse<T> createByErrorCodeMsg(int errorCode, String errorMsg) {
        return new ApiResponse<>(errorCode, errorMsg);
    }

    @Override
    public String toString() {
//        return "{status: " +
        return null;
    }
}

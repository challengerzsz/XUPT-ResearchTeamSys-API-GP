package com.xupt.xiyoumobile.exception;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.BindException;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-26 20:26
 */
@Slf4j
@RestControllerAdvice
public class OverAllApiExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ApiResponse handleUserException(UserException userException) {
        log.error(userException.getApiResponse().getMsg());
        return userException.getApiResponse();
    }

    @ExceptionHandler(BindException.class)
    public ApiResponse<String> handleBindException(BindException bindException) {
        log.error("validate parameter failed! " + bindException.getMessage());
        return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), bindException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getBindingResult().getFieldError().getField() +
                e.getBindingResult().getFieldError().getDefaultMessage());
        return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                e.getBindingResult().getFieldError().getDefaultMessage());
    }
}

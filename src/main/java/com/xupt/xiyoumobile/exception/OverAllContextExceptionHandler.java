package com.xupt.xiyoumobile.exception;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-26 20:26
 */
@ControllerAdvice
@Slf4j
public class OverAllContextExceptionHandler {

    /**
     * 处理所有自定义异常
     * @param e exception
     * @return api rsp
     */
    @ExceptionHandler(UserException.class)
    public ApiResponse handleCustomException(UserException e) {
        log.error(e.getApiResponse().getMsg());
        return e.getApiResponse();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getBindingResult().getFieldError().getField() +
                e.getBindingResult().getFieldError().getDefaultMessage());
        return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                e.getBindingResult().getFieldError().getDefaultMessage());
    }
}

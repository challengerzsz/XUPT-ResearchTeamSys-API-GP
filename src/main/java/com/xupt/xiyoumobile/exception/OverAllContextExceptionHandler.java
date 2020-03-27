package com.xupt.xiyoumobile.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-26 20:26
 */
@ControllerAdvice
@Slf4j
public class OverAllContextExceptionHandler {

    @ExceptionHandler(UserInfoException.class)
    public void handleUserInfoException(UserInfoException e) {
        log.error("UserInfo occurred an error {}", e.getMessage());
    }
}

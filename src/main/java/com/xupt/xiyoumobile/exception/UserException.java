package com.xupt.xiyoumobile.exception;

import com.xupt.xiyoumobile.common.ApiResponse;
import lombok.Getter;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-26 20:25
 */
@Getter
public class UserException extends RuntimeException {

    private ApiResponse apiResponse;

    public UserException(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }
}

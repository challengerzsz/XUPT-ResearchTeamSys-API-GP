package com.xupt.xiyoumobile.web.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-05 20:59
 */
@Data
@AllArgsConstructor
public class ResponseUserToken {

    private String token;
    private User user;

}

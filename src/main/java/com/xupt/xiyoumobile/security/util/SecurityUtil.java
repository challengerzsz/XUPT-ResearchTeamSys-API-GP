package com.xupt.xiyoumobile.security.util;

import com.xupt.xiyoumobile.security.entity.SecurityUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 16:47
 */
public class SecurityUtil {


    private SecurityUtil(){}

    /**
     * 获取当前用户信息
     */
    public static SecurityUser getUserInfo(){
        return (SecurityUser) SecurityContextHolder.getContext().getAuthentication() .getPrincipal();
    }
    /**
     * 获取当前用户ID
     */
    public static Long getUserId(){
        return getUserInfo().getId();
    }
    /**
     * 获取当前用户账号
     */
    public static String getUserAccount(){
        return getUserInfo().getUserAccount();
    }
}

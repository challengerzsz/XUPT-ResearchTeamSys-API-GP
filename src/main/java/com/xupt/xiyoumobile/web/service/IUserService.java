package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Role;
import com.xupt.xiyoumobile.web.entity.User;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-27 09:43
 */
public interface IUserService {


    User selectUserByUserAccount(String username);

    List<Role> selectRoleByUserId(Long id);

    ApiResponse<String> register(User user);
}

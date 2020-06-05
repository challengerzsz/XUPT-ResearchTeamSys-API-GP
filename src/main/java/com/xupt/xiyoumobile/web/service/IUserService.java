package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Role;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.vo.SimpleUserInfoVo;
import com.xupt.xiyoumobile.web.vo.UserRoleVo;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-27 09:43
 */
public interface IUserService {

    User selectUserByUserAccount(String username);

    List<Role> selectRoleByUserId(Long id);

    ApiResponse<String> register(User user, Integer role);

    ApiResponse<String> modifyInfo(User user);

    ApiResponse<User> getUserInfoByUserAccount(String userAccount);

    ApiResponse<String> modifyBanUserStatus(String userAccount, Integer banStatus);

    ApiResponse<List<User>> getAllUsersByRoleId(Integer roleId);

    ApiResponse<List<UserRoleVo>> getAllUserRole();

    ApiResponse<String> resetPassword(String userAccount, String oldPwd, String newPwd);

    ApiResponse<SimpleUserInfoVo> getUserSimpleInfo(Integer type, String userName);

}

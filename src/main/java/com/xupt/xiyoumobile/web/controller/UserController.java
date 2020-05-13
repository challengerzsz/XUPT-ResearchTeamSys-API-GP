package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-04-13 20:06
 */
@Api(description = "UserService Api")
@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUsers/{roleId}")
    public ApiResponse<List<User>> getAllUsers(@PathVariable("roleId") Integer roleId) {
        return userService.getAllUsersByRoleId(roleId);
    }

    @PreAuthorize("hasAnyRole('ADMIN, TEACHER, STUDENT')")
    @GetMapping("/info/{userAccount}")
    public ApiResponse<User> getUserInfo(@PathVariable("userAccount") String userAccount) {
        if (StringUtils.isBlank(userAccount)) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "用户账号为空");
        }

        return userService.getUserInfoByUserAccount(userAccount);
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@Valid User user) {
        if (user == null) {
            return ApiResponse.createByErrorMsg("用户信息为空，注册失败");
        }
        return userService.register(user);
    }

    @PreAuthorize("hasAnyRole('ADMIN, TEACHER, STUDENT')")
    @PostMapping("/modifyInfo")
    public ApiResponse<String> modifyInfo(User user) {
        if (user == null) {
            return ApiResponse.createByErrorMsg("用户信息为空，修改信息失败");
        }
        return userService.modifyInfo(user);
    }

}

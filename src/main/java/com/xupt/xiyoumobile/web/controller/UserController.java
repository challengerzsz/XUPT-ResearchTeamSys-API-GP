package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import com.xupt.xiyoumobile.web.vo.SimpleUserInfoVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-04-13 20:06
 */
@Api(description = "UserService Api")
@Slf4j
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getTeacherInfo/{type}/{userName}")
    public ApiResponse<SimpleUserInfoVo> getUserSimpleInfo(@PathVariable("type") Integer type,
                                                           @PathVariable("userName") String userName) {
        if (type == null || userName == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "查询老师信息失败，参数错误!");
        }

        return userService.getUserSimpleInfo(type, userName);
    }

    @PreAuthorize("hasAnyRole('ADMIN, TEACHER, STUDENT')")
    @GetMapping("/info")
    public ApiResponse<User> getUserInfo(Principal principal) {
        String userAccount = principal.getName();
        if (StringUtils.isBlank(userAccount)) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "用户账号为空");
        }

        return userService.getUserInfoByUserAccount(userAccount);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ApiResponse<String> register(User user, Integer role) {
        if (user == null || role == null) {
            return ApiResponse.createByErrorMsg("用户信息为空，注册失败");
        }
        return userService.register(user, role);
    }

    @PreAuthorize("hasAnyRole('ADMIN, TEACHER, STUDENT')")
    @PostMapping("/modifyInfo")
    public ApiResponse<String> modifyInfo(User user) {
        if (user == null) {
            return ApiResponse.createByErrorMsg("用户信息为空，修改信息失败");
        }
        return userService.modifyInfo(user);
    }

    @PreAuthorize(("hasAnyRole('ADMIN, TEACHER, STUDENT')"))
    @PostMapping("/resetPwd")
    public ApiResponse<String> resetPassword(String userAccount, String oldPwd, String newPwd) {

        if (StringUtils.isAnyBlank(userAccount, oldPwd, newPwd)) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "参数错误");
        }

        return userService.resetPassword(userAccount, oldPwd, newPwd);
    }

}

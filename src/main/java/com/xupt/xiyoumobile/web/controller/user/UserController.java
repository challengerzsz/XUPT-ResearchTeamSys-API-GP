package com.xupt.xiyoumobile.web.controller.user;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.User;
import com.xupt.xiyoumobile.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zengshuaizhi
 * @date : 2020-04-13 20:06
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ApiResponse<String> register(User user) {
        if (user == null) {
            return ApiResponse.createByErrorMsg("用户信息为空，注册失败");
        }
        return userService.register(user);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/test")
    public ApiResponse<String> testAuthentication() {
        return ApiResponse.createBySuccessMsg("管理员权限");
    }

}

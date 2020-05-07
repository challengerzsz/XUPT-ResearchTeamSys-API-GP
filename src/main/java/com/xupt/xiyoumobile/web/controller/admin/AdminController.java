package com.xupt.xiyoumobile.web.controller.admin;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.service.IUserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 22:19
 */
@Api(description = "Admin api")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private IUserService userService;

    @Autowired
    public AdminController(IUserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/ban/{userAccount}/{banStatus}")
    public ApiResponse<String> banUser(@PathVariable("userAccount") String userAccount,
                                       @PathVariable("banStatus") Integer banStatus) {
        if (StringUtils.isBlank(userAccount) || banStatus < 0 || banStatus > 1) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "用户账号为空或禁用状态非法");
        }

        return userService.modifyBanUserStatus(userAccount, banStatus);
    }
}

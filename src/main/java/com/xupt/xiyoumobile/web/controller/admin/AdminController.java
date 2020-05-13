package com.xupt.xiyoumobile.web.controller.admin;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.ResearchDirection;
import com.xupt.xiyoumobile.web.service.IResearchDirectionService;
import com.xupt.xiyoumobile.web.service.IUserService;
import com.xupt.xiyoumobile.web.vo.UserRoleVo;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 22:19
 */
@Api(description = "Admin api")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private IUserService userService;

    private IResearchDirectionService researchDirectionService;

    @Autowired
    public AdminController(IUserService userService, IResearchDirectionService researchDirectionService) {
        this.userService = userService;
        this.researchDirectionService = researchDirectionService;
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllUserRole")
    public ApiResponse<List<UserRoleVo>> getAllUserRole() {
        return userService.getAllUserRole();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllResearchDirections")
    public ApiResponse<List<ResearchDirection>> getAllResearchDirections() {
        return researchDirectionService.getAll();
    }
}

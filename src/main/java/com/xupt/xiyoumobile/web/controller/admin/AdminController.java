package com.xupt.xiyoumobile.web.controller.admin;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.ResearchDirection;
import com.xupt.xiyoumobile.web.service.IAdminService;
import com.xupt.xiyoumobile.web.service.IResearchDirectionService;
import com.xupt.xiyoumobile.web.service.IUserService;
import com.xupt.xiyoumobile.web.vo.AdminClaimExpenseStatisticsVo;
import com.xupt.xiyoumobile.web.vo.CountVo;
import com.xupt.xiyoumobile.web.vo.ArrangeTeamVo;
import com.xupt.xiyoumobile.web.vo.UserRoleVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 22:19
 */
@Slf4j
@Api(description = "Admin api")
@RestController
@RequestMapping("/admin")
public class AdminController {

    private IAdminService adminService;

    private IUserService userService;

    private IResearchDirectionService researchDirectionService;

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public AdminController(IUserService userService, IResearchDirectionService researchDirectionService,
                           IAdminService adminService, SimpMessagingTemplate simpMessagingTemplate) {
        this.userService = userService;
        this.researchDirectionService = researchDirectionService;
        this.adminService = adminService;
        this.simpMessagingTemplate = simpMessagingTemplate;
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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/arrangeTeamMember")
    public ApiResponse<String> arrangeTeamMember(@RequestBody ArrangeTeamVo arrangeTeamVo) {
        if (arrangeTeamVo == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "参数错误");
        }

        return adminService.arrangeTeamMember(arrangeTeamVo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modifyUserRole")
    public ApiResponse<String> modifyUserRole(String userAccount, Integer roleId) {
        if (StringUtils.isBlank(userAccount)) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "参数错误");
        }
        return adminService.modifyUserRole(userAccount, roleId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statistics/claimExpense/{type}")
    public ApiResponse<List<AdminClaimExpenseStatisticsVo>> getAdminClaimExpenseStatistics(
            @PathVariable("type") Integer type,
            @RequestParam String beginDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String typeName) {

        if (type == null || beginDate == null || endDate == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "查询报销统计参数错误!");
        }

        return adminService.getClaimExpenseStatistics(type, typeName, beginDate, endDate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/statistics/achievement/{type}")
    public ApiResponse<Integer> getAchievementStatistics(
            @PathVariable("type") Integer type) {

        if (type == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "查询成果统计参数错误!");
        }

        return adminService.getAchievementStatistics(type);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publishNotice")
    public ApiResponse<String> publishNotice(@RequestParam("notice") String notice) {

        if (notice == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "发布公告信息不能为空!");
        }

        simpMessagingTemplate.convertAndSend("/topic/all", notice);
        return ApiResponse.createBySuccessMsg("发布公告成功");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/countAll")
    public ApiResponse<CountVo> countAll() {
        return adminService.countAll();
    }
}

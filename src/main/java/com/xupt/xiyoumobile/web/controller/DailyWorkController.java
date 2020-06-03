package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.ClaimExpense;
import com.xupt.xiyoumobile.web.entity.Project;
import com.xupt.xiyoumobile.web.service.IDailyWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 19:06
 */
@RestController
@RequestMapping("/dailyWork")
public class DailyWorkController {

    private IDailyWorkService dailyWorkService;

    @Autowired
    public DailyWorkController(IDailyWorkService dailyWorkService) {
        this.dailyWorkService = dailyWorkService;
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @PostMapping("/claimExpense/upload")
    public ApiResponse<String> uploadClaimExpense(Principal principal, ClaimExpense claimExpense) {

        if (claimExpense == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "上传报销记录参数错误!");
        }

        return dailyWorkService.uploadClaimExpense(principal.getName(), claimExpense);
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @PostMapping("/claimExpense/getAllMyClaimExpense")
    public ApiResponse<List<ClaimExpense>> getAllMyClaimExpense(Principal principal) {
        return dailyWorkService.getAllMyClaimExpense(principal.getName());
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @PostMapping("/claimExpense/deleteClaimExpense/{claimExpenseId}")
    public ApiResponse<String> deleteClaimExpense(@PathVariable("claimExpenseId") Integer claimExpenseId,
                                                  Principal principal) {

        if (claimExpenseId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "删除报销记录参数错误!");
        }

        return dailyWorkService.deleteClaimExpense(principal.getName(), claimExpenseId);
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @PostMapping("/claimExpense/modifyClaimExpense")
    public ApiResponse<String> modifyClaimExpense(ClaimExpense claimExpense, Principal principal) {

        if (claimExpense == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "修改报销记录参数错误!");
        }

        return dailyWorkService.modifyClaimExpense(principal.getName(), claimExpense);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/project/upload")
    public ApiResponse<String> uploadProject(Project project) {
        if (project == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "上传项目记录参数错误!");
        }

        return dailyWorkService.uploadProject(project);
    }

    @PreAuthorize("hasRole('TEACHER, STUDENT')")
    @PostMapping("/project/getAll")
    public ApiResponse<List<Project>> getAllProject() {
        return dailyWorkService.getAllProject();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/project/delete/{projectId}")
    public ApiResponse<String> deleteProject(@PathVariable("projectId") Integer projectId) {
        if (projectId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "删除项目记录参数错误!");
        }

        return dailyWorkService.deleteProject(projectId);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/project/modifyProject")
    public ApiResponse<String> modifyProject(Project project) {

        if (project == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "修改项目记录参数错误!");
        }

        return dailyWorkService.modifyProject(project);
    }
}

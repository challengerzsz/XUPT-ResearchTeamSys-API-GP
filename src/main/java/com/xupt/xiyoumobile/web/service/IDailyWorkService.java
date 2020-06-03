package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.ClaimExpense;
import com.xupt.xiyoumobile.web.entity.Project;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 19:10
 */
public interface IDailyWorkService {

    ApiResponse<String> uploadClaimExpense(String userAccount, ClaimExpense claimExpense);

    ApiResponse<List<ClaimExpense>> getAllMyClaimExpense(String userAccount);

    ApiResponse<String> deleteClaimExpense(String userAccount, Integer claimExpenseId);

    ApiResponse<String> modifyClaimExpense(String userAccount, ClaimExpense claimExpense);

    ApiResponse<String> uploadProject(Project project);

    ApiResponse<List<Project>> getAllProject();

    ApiResponse<String> deleteProject(Integer projectId);

    ApiResponse<String> modifyProject(Project project);
}

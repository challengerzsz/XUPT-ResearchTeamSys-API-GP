package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.vo.AdminClaimExpenseStatisticsVo;
import com.xupt.xiyoumobile.web.vo.CountVo;
import com.xupt.xiyoumobile.web.vo.ArrangeTeamVo;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-22 11:14
 */
public interface IAdminService {

    ApiResponse<String> arrangeTeamMember(ArrangeTeamVo arrangeTeamVo);

    ApiResponse<String> modifyUserRole(String userAccount, Integer roleId);

    ApiResponse<List<AdminClaimExpenseStatisticsVo>> getClaimExpenseStatistics(Integer type, String typeName, String beginDate,
                                                                               String endDate);

    ApiResponse<Integer> getAchievementStatistics(Integer type);

    ApiResponse<CountVo> countAll();
}

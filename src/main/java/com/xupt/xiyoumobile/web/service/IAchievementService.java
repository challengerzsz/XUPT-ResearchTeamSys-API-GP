package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Competition;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 13:52
 */
public interface IAchievementService {

    ApiResponse<String> uploadCompetition(Competition competition);

    ApiResponse<List<Competition>> getAllCompetition();

    ApiResponse<String> deleteCompetition(Integer competitionId);

    ApiResponse<String> modifyCompetition(Competition competition);
}

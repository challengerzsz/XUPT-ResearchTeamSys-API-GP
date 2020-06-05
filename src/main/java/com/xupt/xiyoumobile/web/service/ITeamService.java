package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.Team;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-05 16:39
 */
public interface ITeamService {

    ApiResponse<String> createTeam(Team team);

    ApiResponse<Team> getTeamInfo(String teacherName);

    ApiResponse<String> deleteTeam(Integer teamId);

    ApiResponse<String> modifyTeamInfo(Team team);

    ApiResponse<List<Team>> getAllTeam();

}

package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.Team;
import com.xupt.xiyoumobile.web.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-05 16:32
 */
@RestController
@RequestMapping("/team")
public class TeamController {

    private ITeamService teamService;

    @Autowired
    public TeamController(ITeamService teamService) {
        this.teamService = teamService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createTeam")
    public ApiResponse<String> createTeam(Team team) {
        if (team == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "新建小组失败,参数错误!");
        }

        return teamService.createTeam(team);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getTeamInfo/{teacherName}")
    public ApiResponse<Team> getTeamInfo(@PathVariable("teacherName") String teacherName) {
        if (teacherName == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "查询小组信息失败,参数错误!");
        }

        return teamService.getTeamInfo(teacherName);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/deleteTeam/{teamId}")
    public ApiResponse<String> deleteTeam(@PathVariable("teamId") Integer teamId) {
        if (teamId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "删除小组失败,参数错误!");
        }

        return teamService.deleteTeam(teamId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/modifyTeamInfo")
    public ApiResponse<String> modifyTeamInfo(Team team) {
        if (team == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "修改小组信息失败,参数错误!");
        }

        return teamService.modifyTeamInfo(team);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getAllTeam")
    public ApiResponse<List<Team>> getAllTeam() {
        return teamService.getAllTeam();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getTeamInfoByTeacherAccount/{teacherAccount}")
    public ApiResponse<List<Team>> getTeamInfoByTeacherAccount(@PathVariable("teacherAccount") Integer teacherAccount) {
        if (teacherAccount == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "获取老师指导小组失败,参数错误!");
        }

        return teamService.getTeamInfoByTeacherAccount(teacherAccount);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/getMyTeamInfo")
    public ApiResponse<Team> getMyTeamInfo(Principal principal) {
        return teamService.getMyTeamInfo(principal.getName());
    }
}

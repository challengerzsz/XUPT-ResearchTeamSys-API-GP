package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.Competition;
import com.xupt.xiyoumobile.web.service.IAchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 13:32
 */
@RestController
@RequestMapping("/achievement")
public class AchievementController {

    private IAchievementService achievementService;

    @Autowired
    public AchievementController(IAchievementService achievementService) {
        this.achievementService = achievementService;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/competition/upload")
    public ApiResponse<String> uploadCompetition(Competition competition) {
        if (competition == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "上传竞赛信息参数错误");
        }

        return achievementService.uploadCompetition(competition);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/competition/delete/{competitionId}")
    public ApiResponse<String> deleteCompetition(@PathVariable("competitionId") Integer competitionId) {
        if (competitionId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "删除竞赛参数错误!");
        }

        return achievementService.deleteCompetition(competitionId);
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @GetMapping("/competition/getAll")
    public ApiResponse<List<Competition>> getAllCompetition() {
        return achievementService.getAllCompetition();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/competition/modify")
    public ApiResponse<String> modifyCompetition(Competition competition) {

        if (competition == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "修改竞赛信息参数错误!");
        }

        return achievementService.modifyCompetition(competition);
    }

}

package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.Competition;
import com.xupt.xiyoumobile.web.entity.Patent;
import com.xupt.xiyoumobile.web.entity.SoftWareCopyright;
import com.xupt.xiyoumobile.web.service.IAchievementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 13:32
 */
@Slf4j
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

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/patent/upload")
    public ApiResponse<Integer> uploadPatent(Patent patent) {
        if (patent == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "上传专利信息参数错误!");
        }

        return achievementService.uploadPatent(patent);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/patent/uploadFile/{patentId}")
    public ApiResponse<String> uploadPatentFile(@PathVariable("patentId") Integer patentId,
                                                Principal principal,
                                                @RequestParam("file") MultipartFile multipartFile) {

        if (patentId == null || multipartFile == null || multipartFile.isEmpty()) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "上传专利附件参数错误!");
        }

        return achievementService.uploadPatentFile(principal.getName(), patentId, multipartFile);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/patent/delete/{patentId}")
    public ApiResponse<String> deletePatent(@PathVariable("patentId") Integer patentId) {

        if (patentId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "删除专利参数错误!");
        }

        return achievementService.deletePatent(patentId);
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @GetMapping("/patent/getAll")
    public ApiResponse<List<Patent>> getAllPatent() {
        return achievementService.getAllPatent();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/patent/modify")
    public ApiResponse<String> modifyPatent(Patent patent) {

        if (patent == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "修改专利信息参数错误!");
        }

        return achievementService.modifyPatent(patent);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/softWareCopyright/upload")
    public ApiResponse<Integer> uploadSoftWareCopyright(SoftWareCopyright softWareCopyright) {
        if (softWareCopyright == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "上传软件著作权信息参数错误!");
        }

        return achievementService.uploadSoftWareCopyright(softWareCopyright);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/softWareCopyright/uploadFile/{softWareCopyrightId}/{type}")
    public ApiResponse<String> uploadSoftWareCopyrightFile(@PathVariable("softWareCopyrightId") Integer softWareCopyrightId,
                                                           @RequestParam(value = "file") MultipartFile file,
                                                           @PathVariable("type") Integer type,
                                                           Principal principal) {

        if (softWareCopyrightId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "上传软件著作权附件参数错误!");
        }

        return achievementService.uploadSoftWareCopyrightFile(principal.getName(), softWareCopyrightId, file, type);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/softWareCopyright/modify")
    public ApiResponse<String> modifySoftWareCopyright(SoftWareCopyright softWareCopyright) {

        if (softWareCopyright == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "修改软件著作权信息参数错误!");
        }

        return achievementService.modifySoftWareCopyright(softWareCopyright);
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @GetMapping("/softWareCopyright/getAll")
    public ApiResponse<List<SoftWareCopyright>> getAllSoftWareCopyright() {
        return achievementService.getAllSoftWareCopyright();
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/softWareCopyright/delete/{scId}")
    public ApiResponse<String> deleteSoftWareCopyright(@PathVariable("scId") Integer scId) {
        if (scId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "删除软件著作权参数错误!");
        }

        return achievementService.deleteSoftWareCopyright(scId);
    }

}

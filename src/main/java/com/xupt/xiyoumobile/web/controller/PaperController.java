package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.Paper;
import com.xupt.xiyoumobile.web.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:32
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    private IPaperService paperService;

    @Autowired
    public PaperController(IPaperService paperService) {
        this.paperService = paperService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/upload")
    public ApiResponse<Integer> uploadPaper(Principal principal, Paper paper) {

        if (paper == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "参数错误!");
        }

        return paperService.upload(principal.getName(), paper);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/uploadFile/{paperId}")
    public ApiResponse<String> uploadPaperFile(Principal principal, @RequestParam("file") MultipartFile multipartFile,
                                               @PathVariable("paperId") Integer paperId) {

        if (paperId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "上传论文参数错误");
        }

        return paperService.uploadPaperFile(principal.getName(), paperId, multipartFile);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/getMyPaper/{type}")
    public ApiResponse<List<Paper>> getMyPaper(Principal principal, @PathVariable("type") Integer type) {
        if (type == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "参数错误");
        }

        return paperService.getMyPaper(principal.getName(), type);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/getStudentPaper/{userAccount}/{type}")
    public ApiResponse<List<Paper>> getStudentPaper(@PathVariable("userAccount") String userAccount,
                                              @PathVariable("type") Integer type) {
        if (userAccount == null || type == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "参数错误");
        }

        return paperService.getStudentPaper(userAccount, type);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/modifyPaper")
    public ApiResponse<String> modifyPaper(Principal principal, Paper paper) {
        if (paper == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "参数错误");
        }

        return paperService.modifyPaper(principal.getName(), paper);
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/getMyStudentPapers/{type}")
    public ApiResponse<List<Paper>> getMyStudentPapers(@PathVariable("type") Integer type,
                                                       Principal principal) {
        if (type == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "查看指导学生论文参数错误");
        }

        return paperService.getMyStudentPapers(principal.getName(), type);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/deletePaper/{paperId}/{type}")
    public ApiResponse<String> deleteSmallPaper(@PathVariable("type") Integer type, @PathVariable("paperId") Integer paperId) {
        if (type == null || paperId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "删除小论文参数错误");
        }

        return paperService.deleteSmallPaper(type, paperId);
    }
}

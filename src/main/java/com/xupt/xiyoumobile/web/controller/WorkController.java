package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.WorkReport;
import com.xupt.xiyoumobile.web.service.IWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-15 03:09
 */
@RestController
@RequestMapping("/work")
public class WorkController {

    private IWorkService workService;

    @Autowired
    public WorkController(IWorkService workService) {
        this.workService = workService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/report/upload/{type}")
    public ApiResponse<String> uploadReport(Principal principal, WorkReport workReport,
                                            @PathVariable("type") Integer type) {
        if (workReport == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "上传报告参数错误!");
        }

        return workService.uploadReport(principal.getName(), workReport, type);
    }

    @PreAuthorize("hasRole('STUDEN')")
    @PostMapping("/report/uploadFile/{type}")
    public ApiResponse<String> uploadReportFile(Principal principal, MultipartFile multipartFile,
                                                @PathVariable("type") Integer type) {

        if (multipartFile == null || multipartFile.isEmpty()) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "上传报告附件参数错误");
        }

        return workService.uploadReportFile(principal.getName(), multipartFile, type);
    }

    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @GetMapping("/report/{type}")
    public ApiResponse<WorkReport> getReport(Principal principal, @PathVariable("type") Integer type) {
        if (type == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "报告类型参数错误!");
        }

        return workService.getReport(principal.getName(), type);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/report/modify/{type}")
    public ApiResponse<String> modifyWorkReport(Principal principal, WorkReport workReport,
                                                    @PathVariable("type") Integer type) {
        if (type == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "报告类型参数错误!");
        }

        return workService.modifyWorkReport(principal.getName(), workReport, type);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/report/delete/{type}")
    public ApiResponse<String> deleteWorkReport(Principal principal, Integer workReportId,
                                                @PathVariable("type") Integer type) {
        if (type == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "报告类型参数错误!");
        }

        return workService.deleteWorkReport(principal.getName(), workReportId);
    }
}

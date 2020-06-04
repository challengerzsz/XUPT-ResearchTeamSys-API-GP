package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import com.xupt.xiyoumobile.web.entity.WeeklyReport;
import com.xupt.xiyoumobile.web.service.IWeeklyReportService;
import com.xupt.xiyoumobile.web.vo.WeeklyReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-14 23:41
 */
@RestController
@RequestMapping("/weeklyReport")
public class WeeklyReportController {

    private IWeeklyReportService weeklyReportService;

    @Autowired
    public WeeklyReportController(IWeeklyReportService weeklyReportService) {
        this.weeklyReportService = weeklyReportService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/uploadWeeklyReport")
    public ApiResponse<String> uploadWeeklyReport(Principal principal, WeeklyReport weeklyReport) {

        if (weeklyReport == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "周报内容为空参数错误!");
        }

        return weeklyReportService.uploadWeeklyReport(principal.getName(), weeklyReport);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/getAllWeeklyReport")
    public ApiResponse<List<WeeklyReport>> getOldWeeklyReports(Principal principal) {
        return weeklyReportService.getOldWeeklyReports(principal.getName());
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/comment/{weeklyReportId}")
    public ApiResponse<String> commentOnWeeklyReport(@PathVariable("weeklyReportId") Integer weeklyReportId,
                                                     String comment, Principal principal) {
        if (weeklyReportId == null || comment == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(), "批注周报参数错误!");
        }

        return weeklyReportService.commentOnWeeklyReport(principal.getName(), weeklyReportId, comment);
    }


    @PreAuthorize("hasAnyRole('TEACHER, STUDENT')")
    @GetMapping("/getWeeklyReport/{weeklyReportId}")
    public ApiResponse<WeeklyReportVo> getWeeklyReport(@PathVariable("weeklyReportId") Integer weeklyReportId) {

        if (weeklyReportId == null) {
            return ApiResponse.createByErrorCodeMsg(ApiRspCode.ILLEGAL_ARGUMENT.getCode(),
                    "查看周报信息参数错误!");
        }

        return weeklyReportService.getWeeklyReport(weeklyReportId);
    }

}

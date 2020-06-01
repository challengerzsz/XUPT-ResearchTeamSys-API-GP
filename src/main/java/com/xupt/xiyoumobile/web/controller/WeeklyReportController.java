package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.WeeklyReport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-14 23:41
 */
@RestController
@RequestMapping("/weeklyReport")
public class WeeklyReportController {

    @PostMapping
    public ApiResponse<String> updateWeekReport(WeeklyReport weeklyReport) {
        return null;
    }

    @GetMapping
    public ApiResponse<List<WeeklyReport>> getOldWeeklyReports() {
        return null;
    }


}

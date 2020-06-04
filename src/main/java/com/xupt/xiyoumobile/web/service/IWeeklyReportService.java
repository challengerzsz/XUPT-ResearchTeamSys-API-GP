package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.WeeklyReport;
import com.xupt.xiyoumobile.web.vo.WeeklyReportVo;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-04 15:49
 */
public interface IWeeklyReportService {

    ApiResponse<String> uploadWeeklyReport(String userAccount, WeeklyReport weeklyReport);

    ApiResponse<List<WeeklyReport>> getOldWeeklyReports(String userAccount);

    ApiResponse<String> commentOnWeeklyReport(String userAccount, Integer weeklyReportId, String comment);

    ApiResponse<WeeklyReportVo> getWeeklyReport(Integer weeklyReportId);
}

package com.xupt.xiyoumobile.web.service;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.web.entity.WorkReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 16:14
 */
public interface IWorkService {

    ApiResponse<String> uploadReport(String userAccount, WorkReport openingReport, Integer type);

    ApiResponse<WorkReport> getReport(String userAccount, Integer type);

    ApiResponse<String> uploadReportFile(String userAccount, MultipartFile multipartFile, Integer type);

    ApiResponse<String> modifyWorkReport(String userAccount, WorkReport workReport, Integer type);

    ApiResponse<String> deleteWorkReport(String userAccount, Integer workReportId);

    ApiResponse<List<WorkReport>> getTeamWorkReports(String userAccount, Integer type);
}

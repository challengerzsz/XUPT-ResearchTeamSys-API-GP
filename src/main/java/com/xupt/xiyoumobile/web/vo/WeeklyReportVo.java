package com.xupt.xiyoumobile.web.vo;

import com.xupt.xiyoumobile.web.entity.WeeklyReport;
import com.xupt.xiyoumobile.web.entity.WeeklyReportComment;
import lombok.Data;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-04 20:26
 */
@Data
public class WeeklyReportVo {

    private WeeklyReport weeklyReport;
    private List<WeeklyReportComment> comments;

    public WeeklyReportVo(WeeklyReport weeklyReport, List<WeeklyReportComment> comments) {
        this.weeklyReport = weeklyReport;
        this.comments = comments;
    }
}

package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-15 00:04
 */
@Data
public class WeeklyReportComment {

    private Integer id;
    private Integer weeklyReportId;
    private String teacherAccount;
    private String teacherName;
    private String content;
    private Timestamp create_time;

}

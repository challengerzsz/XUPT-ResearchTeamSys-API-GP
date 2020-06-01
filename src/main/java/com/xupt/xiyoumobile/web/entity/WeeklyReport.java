package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-14 23:59
 */
@Data
public class WeeklyReport {
    private Integer id;
    private String userAccount;
    private String reportContent;
    private Timestamp createTime;
}

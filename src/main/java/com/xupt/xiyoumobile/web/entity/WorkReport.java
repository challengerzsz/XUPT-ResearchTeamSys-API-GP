package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 16:08
 */
@Data
public class WorkReport {

    private Integer id;
    private String paperName;
    private String author;
    private String reportUrl;
    private Integer type;
    private Timestamp createTime;
    private Timestamp updateTime;
}

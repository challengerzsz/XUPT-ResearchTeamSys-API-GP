package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 14:31
 */
@Data
public class Patent {

    private Integer id;
    private String patentName;
    private String members;
    private String filePath;
    private String status;
    private String patentTime;
    private Timestamp createTime;
    private Timestamp updateTime;
}

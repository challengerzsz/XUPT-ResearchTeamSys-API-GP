package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 19:40
 */
@Data
public class Project {

    private Integer id;
    private String projectNo;
    private String projectName;
    private String type;
    private String level;
    private String hostName;
    private String members;
    private Timestamp createTime;
    private Timestamp updateTime;
}

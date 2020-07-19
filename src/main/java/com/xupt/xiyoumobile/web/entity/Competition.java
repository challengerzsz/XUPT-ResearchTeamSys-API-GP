package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 13:50
 */
@Data
public class Competition {

    private Integer id;
    private String competitionName;
    private String category;
    private String level;
    private String members;
    private String competitionTime;
    private String filePath;
    private Timestamp createTime;
    private Timestamp updateTime;
}

package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-18 17:29
 */
@Data
public class Team {

    private Integer id;
    private String guideTeacherAccount;
    private String guideTeacherName;
    private String teamName;
    private String teamImg;
    private Integer studentCount;
    private Integer teamDirection;
    private Timestamp createTime;
    private Timestamp updateTime;

}

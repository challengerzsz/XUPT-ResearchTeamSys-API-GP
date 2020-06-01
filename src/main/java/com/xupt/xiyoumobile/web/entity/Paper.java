package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-15 03:10
 */
@Data
public class Paper {
    private int id;
    private String author;
    private String topic;
    private String abstractContent;
    private String content;
    private Timestamp createTime;
    private Timestamp updateTime;
}

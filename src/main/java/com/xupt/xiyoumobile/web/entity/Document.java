package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-01 18:41
 */
@Data
public class Document {
    private Integer id;
    private String author;
    private String direction;
    private String topic;
    private String abstractContent;
    private String organ;
    private String pdfUrl;
    private String useMethod;
    private String innovation;
    private String originPath;
    private Timestamp createTime;
    private Timestamp updateTime;
}

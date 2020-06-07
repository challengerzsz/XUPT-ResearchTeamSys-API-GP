package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-02 19:30
 */
@Data
public class Paper {

    private Integer id;
    private String topic;
    private String author;
    private String authorName;
    private String abstractZh;
    private String abstractEn;
    private String pdfUrl;
    private String acceptTime;
    private Integer type;
    private Timestamp createTime;
    private Timestamp updateTime;
}

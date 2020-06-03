package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 15:28
 */
@Data
public class SoftWareCopyright {
    private Integer id;
    private String softwareCopyrightName;
    private String members;
    private String documentPath;
    private String projectPath;
    private String certificatePath;
    private String time;
    private Timestamp createTime;
    private Timestamp updateTime;
}

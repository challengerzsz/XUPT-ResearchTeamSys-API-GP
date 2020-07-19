package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.sql.Date;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-15 03:18
 */
@Data
public class DocumentComment {

    private Integer id;
    private Integer paperId;
    private String content;
    private String userAccount;
    private String userName;
    private Date createTime;
}

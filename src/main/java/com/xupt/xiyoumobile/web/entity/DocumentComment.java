package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

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
    private Integer type;
    private Integer reCommentId;
}

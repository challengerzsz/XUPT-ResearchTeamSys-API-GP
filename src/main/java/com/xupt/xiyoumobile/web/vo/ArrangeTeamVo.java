package com.xupt.xiyoumobile.web.vo;

import lombok.Data;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-22 11:06
 */
@Data
public class ArrangeTeamVo {

    private Integer teamId;
    private String guideTeacherAccount;
    private String guideTeacherName;
    private List<String> memberAccountList;
}

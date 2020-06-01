package com.xupt.xiyoumobile.web.vo;

import lombok.Data;

import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-22 11:06
 */
@Data
public class TeamMemberVo {

    private Integer teamId;
    private List<String> memberIdList;
}

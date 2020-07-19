package com.xupt.xiyoumobile.web.entity;

import com.xupt.xiyoumobile.web.vo.SimpleUserInfoVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-18 17:29
 */
@Data
public class Team {

    private Integer id;
    private List<SimpleUserInfoVo> guideTeachers;
    private String teamName;
    private String teamImg;
    private Integer studentCount;
    private String teamDirection;
    private Timestamp createTime;
    private Timestamp updateTime;

}

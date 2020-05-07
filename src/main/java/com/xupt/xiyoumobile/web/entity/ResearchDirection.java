package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-07 22:53
 */
@Data
public class ResearchDirection {

    private Integer id;
    @NotNull
    private String directionName;
    private Timestamp createTime;
    private Timestamp updateTime;
}

package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 17:41
 */
@Data
public class Role implements Serializable {

    private static final long serialVersionUID = -4602622606260111192L;
    /**
     * 角色ID
     */
    private Long id;
    /**
     * 角色名称
     */
    private String roleName;
}

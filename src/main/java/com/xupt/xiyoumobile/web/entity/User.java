package com.xupt.xiyoumobile.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 16:52
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = 6427095107443358456L;

    private Long id;
    private String userAccount;
    @JsonIgnore
    @NotEmpty(message = "password can not be null")
    private String userPassword;
    private String img;
    private String userName;
    private String major;
    private Integer grade;
    private String classify;
    private Integer team;
    private Integer guideTeacherId;
    private String personalSignature;
    private Integer researchDirection;
    private Integer sex;
    private Integer ban;

}

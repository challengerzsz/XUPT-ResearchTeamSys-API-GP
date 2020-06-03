package com.xupt.xiyoumobile.web.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-03 19:01
 */
@Data
public class ClaimExpense {

    private Integer id;
    private String userAccount;
    private String userName;
    private String type;
    private BigDecimal unitPrice;
    private Integer amount;
    private BigDecimal totalPrice;
    private String date;
    private String comment;
    private String status;
    private Timestamp createTime;
    private Timestamp updateTime;
}

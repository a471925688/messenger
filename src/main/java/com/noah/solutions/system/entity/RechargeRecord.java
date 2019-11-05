package com.noah.solutions.system.entity;

import com.noah.solutions.common.ProjectConfig;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 充值记录表
 */
@Entity
public class RechargeRecord implements Serializable {


    private static final long serialVersionUID = -4413501715717712522L;
    @Id
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '自增id'",insertable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String rechargeId;  // 自增id

    @Column(columnDefinition = "DOUBLE  COMMENT '(充值数量)'",nullable = false)
    private Double rechargeAmount;  // 充值数量

    @Column(columnDefinition = "VARCHAR(500) COMMENT '描述'")
    private String rechargeComments;  // 描述

    @Column(columnDefinition = "INT(10)  COMMENT '会员id'",nullable = false)
    private Integer userId;  // 操作员id

    @Column(columnDefinition = "INT(10)  COMMENT '操作员id'",nullable = false)
    private Integer operatorId;  // 操作员id

    @Column(columnDefinition = "INT(4) COMMENT '充值方式'",nullable = false)
    private Integer rechargeMode;  // 充值方式

    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",insertable = false,updatable = false)
    private Date createTime;  // 充值时间

    @Transient
    private String username;//充值账号

    @Transient
    private String operatorname;//操作员账号

    @Transient
    private String rechargeModeStr;//充值方式

    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="userId",insertable = false,updatable = false)
    private User user;


    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="operatorId",insertable = false,updatable = false)
    private User operator;


    public RechargeRecord() {
    }

    public RechargeRecord(Double rechargeAmount, Integer userId, Integer operatorId, Integer rechargeMode, String rechargeComments) {
        this.rechargeAmount = rechargeAmount;
        this.userId = userId;
        this.operatorId = operatorId;
        this.rechargeMode = rechargeMode;
        this.rechargeComments = rechargeComments;
    }

    public String getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Double getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Double rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public String getRechargeComments() {
        return rechargeComments;
    }

    public void setRechargeComments(String rechargeComments) {
        this.rechargeComments = rechargeComments;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getRechargeMode() {
        return rechargeMode;
    }

    public void setRechargeMode(Integer rechargeMode) {
        this.rechargeMode = rechargeMode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public void setRechargeModeStr(String rechargeModeStr) {
        this.rechargeModeStr = rechargeModeStr;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getOperator() {
        return operator;
    }

    public void setOperator(User operator) {
        this.operator = operator;
    }

    public String getUsername() {
        if(user!=null){
            username=user.getUsername();
        }
        return username;
    }


    public String getOperatorname() {
        if(operator!=null){
            operatorname=operator.getUsername();
        }
        return operatorname;
    }


    public String getRechargeModeStr() {
        if(!StringUtils.isEmpty(rechargeMode))
            rechargeModeStr = ProjectConfig.RechargeMode.queryValue(rechargeMode);
        return rechargeModeStr;
    }

}
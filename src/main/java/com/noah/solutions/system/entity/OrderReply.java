package com.noah.solutions.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 2018 11 26 lijun
 *
 */
@Entity
@Table(name = "order_reply")
@Data
public class OrderReply implements Serializable {

    private static final long serialVersionUID = -1491848324484992950L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT COMMENT '自增id'")
    private String orderReplyId;
    @Column(columnDefinition = "INT COMMENT '快递单号'",nullable = false)
    private String  orderId;
    @Column(columnDefinition = "INT COMMENT '用戶'",nullable = false)
    private String  userId;
    @Column(columnDefinition = "INT(4) DEFAULT 0 COMMENT '是否已讀'",insertable = false,nullable = false)
    private Boolean  isRead;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '留言信息'")
    private String  info;
    @Column(columnDefinition = "INT(4) COMMENT '是否用戶留言(1.是,0.否)'")
    private Boolean  isMember;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date  createTime;


    //用户信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OrderReply() {
    }

    public OrderReply(String orderId, String userId,Boolean isMember, String info) {
        this.orderId = orderId;
        this.userId = userId;
        this.info = info;
        this.isMember = isMember;
    }

    public Boolean getIsMember() {
        return isMember;
    }

    public void setIsMember(Boolean isMember) {
        this.isMember = isMember;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getOrderReplyId() {
        return orderReplyId;
    }

    public void setOrderReplyId(String orderReplyId) {
        this.orderReplyId = orderReplyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

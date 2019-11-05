package com.noah.solutions.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name ="message",
        indexes = {@Index(columnList = "userId"),@Index(columnList = "isRead")} )
public class Message implements Serializable {
    private static final long serialVersionUID = -4301724877224420820L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT COMMENT '自增id'")
    private String messageId;
    @Column(columnDefinition = "INT(4) COMMENT '消息類型(1.系統消息,2.訂單消息)'",nullable = false)
    private Integer  messageType;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '所屬用戶id'",nullable = false)
    private String  userId;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '標題'")
    private String  messageTitle;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '消息內容'")
    private String  messageContent;
    @Column(columnDefinition = "INT(4) DEFAULT 0 COMMENT '是否已讀'",insertable = false,nullable = false)
    private Boolean  isRead;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date  createTime;

    public Message() {
    }

    public Message(Integer messageType, String userId,String messageTitle,String messageContent) {
        this.messageType = messageType;
        this.userId = userId;
        this.messageTitle = messageTitle;
        this.messageContent = messageContent;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

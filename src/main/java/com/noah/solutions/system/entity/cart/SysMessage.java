package com.noah.solutions.system.entity.cart;

import javax.persistence.*;

/**
 * 系統消息
 */
//@Entity
public class SysMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT AUTO_INCREMENT  COMMENT '自增id'")
    private Integer id;//發送人id
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '头像访问地址'")
    private String avatar;//頭像
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '內容'")
    private String content;//內容
    @Column(name ="sys_from",columnDefinition = "INT COMMENT '來源'")
    private Integer from;//發送人id
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '內容'")
    private String username;//發送人名字
    @Column(columnDefinition = "INT(4) COMMENT '消息類型1.好友申請'")
    private String type;//消息類型
    @Column(columnDefinition = "INT COMMENT '分組id'")
    private String groupId;//分組id
    @Column(columnDefinition = "INT COMMENT '接收人'")
    private Integer uid;//發送對象id
    @Column(name = "is_read",columnDefinition = "INT DEFAULT 0 COMMENT '是否已讀'")
    private Boolean read;//是否已讀
    @Column(columnDefinition = "INT DEFAULT 0 COMMENT '處理狀態'")
    private Integer status;//處理狀態


    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private String  createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private String  updateTime;
    @Transient
    private Boolean mine=false;//是否自己

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getMine() {
        return mine;
    }

    public void setMine(Boolean mine) {
        this.mine = mine;
    }
}

package com.noah.solutions.system.entity;


import com.alibaba.druid.util.StringUtils;
import com.noah.solutions.common.utils.StringUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 中轉站信息
 */
@Entity
public class TransferStation implements Serializable {
    private static final long serialVersionUID = 4860295298769212118L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '中轉站id'")
    private Integer id;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(店名)'")
    private String name;
    @Column(columnDefinition = "VARCHAR(60) NOT NULL COMMENT '(电话)'")
    private String mobilePhone;
    @Column(columnDefinition = "VARCHAR(150) NOT NULL COMMENT '(電郵)'")
    private String email;
    @Column(columnDefinition = "VARCHAR(500) NOT NULL COMMENT '详细地址'")
    private String  address;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(公司簡介)'")
    private String intro;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;  // 注册时间

    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '修改时间'",insertable=false)
    private Date updateTime;  // 修改时间


    @Transient
    private String phoneHeader;
    @Transient
    private String phoneContent;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneHeader() {
        if(!StringUtils.isEmpty(mobilePhone)&&mobilePhone.contains("-")){
            phoneHeader = mobilePhone.split("-")[0];
        }
        return phoneHeader;
    }

    public void setPhoneHeader(String phoneHeader) {
        this.phoneHeader = phoneHeader;
    }

    public String getPhoneContent() {
        if(!StringUtils.isEmpty(mobilePhone)&&mobilePhone.contains("-")){
            phoneContent = mobilePhone.split("-")[1];
        }
        return phoneContent;
    }

    public void setPhoneContent(String phoneContent) {
        this.phoneContent = phoneContent;
    }
}

package com.noah.solutions.system.entity;

import com.noah.solutions.common.ProjectConfig;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 */
@Entity
@Table(name = "sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = -393463908673953530L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;  // 主键id

    private String username;  // 账号

    private String password;  // 密码

    private String nickName;  // 昵称

    private String avatar;  // 头像

    private String sex;  // 性别

    private String areaCode;  // 手机号

    private String phone;  // 手机号

    @Column(insertable = false)
    private Double tokenMoney;//代币

    @Column(columnDefinition = "INT  DEFAULT 0 COMMENT '更新时间'",insertable=false,nullable = false)
    private Long Integral;//積分

    private Integer type;  // 用户类型

    @Column(insertable = false)
    private Integer state;  // 用户状态，0正常，1锁定

    private String appId;  // 消息推送的id

    @Column(insertable = false,updatable = false)
    private Date createTime;  // 注册时间

    @Column(insertable = false)
    private Date updateTime;  // 修改时间

    @Transient
    private List<Role> roles;  //角色

    @Transient
    private String  headPortraitPath;  //角色

    @Transient
    private  Integer userType;


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getHeadPortraitPath() {
        if(!StringUtils.isEmpty(avatar))
            headPortraitPath = ProjectConfig.PROJECT_PATH+ "image/"+avatar;
        return headPortraitPath;
    }

    public void setHeadPortraitPath(String headPortraitPath) {
        this.headPortraitPath = headPortraitPath;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getTokenMoney() {
        return tokenMoney;
    }

    public void setTokenMoney(Double tokenMoney) {
        this.tokenMoney = tokenMoney;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public Long getIntegral() {
        return Integral;
    }

    public void setIntegral(Long integral) {
        Integral = integral;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}

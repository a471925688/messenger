package com.noah.solutions.system.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志
 */
@Entity
@Table(name = "sys_login_record")
public class LoginRecord implements Serializable {
    private static final long serialVersionUID = -8198349265848156122L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键

    private Integer userId;  // 用户id

    private String osName;  // 操作系统

    private String device;  // 设备型号

    private String browserType;  // 浏览器类型

    private String ipAddress;  // ip地址

    @Column(insertable = false,updatable = false)
    private Date createTime;  // 登录时间

    @Transient
    private String nickName;  // 用户昵称

    @Transient
    private String username;  // 用户账号



    @NotFound(action= NotFoundAction.IGNORE)
    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="userId",insertable = false,updatable = false)
    private User user;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getBrowserType() {
        return browserType;
    }

    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNickName() {
        if(user!=null)
            nickName=user.getNickName();
        return nickName;
    }


    public String getUsername() {
        if(user!=null)
            username=user.getUsername();
        return username;
    }

}
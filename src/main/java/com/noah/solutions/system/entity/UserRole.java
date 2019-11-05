package com.noah.solutions.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户角色关联表
 * 如果你的用户只对应一个角色，把前台的多选select改成单选即可，不需要改表结构
 */
@Entity
@Table(name = "sys_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = -7172241268951193848L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键

    private Integer userId;  // 用户id

    private Integer roleId;  // 角色id

    @Column(insertable = false,updatable = false)
    private Date createTime;  // 创建时间

    @Transient
    private String roleName;  // 角色名称

    public UserRole() {
    }

    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
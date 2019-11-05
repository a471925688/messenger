package com.noah.solutions.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色表
 */
@Entity
@Table(name = "sys_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 4953960972725745735L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;  // 角色id

    private String roleName;  // 角色名称

    private String comments;  // 描述

    private Integer isDelete;  // 逻辑删除，0未删除，1已删除

    @Column(insertable = false,updatable = false)
    private Date createTime;  // 创建时间

    @Column(insertable = false)
    private Date updateTime;  // 修改时间

    public Role() {
    }

    public Role(Integer roleId) {
        setRoleId(roleId);
    }

    public Role(Integer roleId, String roleName) {
        setRoleId(roleId);
        setRoleName(roleName);
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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
}
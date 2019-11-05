package com.noah.solutions.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 角色权限关联表
 */
@Entity
@Table(name = "sys_role_authorities")
public class RoleAuthorities implements Serializable {
    private static final long serialVersionUID = -4825576219511603825L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;  // 主键，也可以用联合主键，根据个人习惯

    private Integer roleId;  // 角色id

    private Integer authorityId;  // 权限id

    @Column(insertable = false,updatable = false)
    private Date createTime;  // 添加时间

    public RoleAuthorities() {
    }

    public RoleAuthorities(Integer roleId, Integer authorityId) {
        this.roleId = roleId;
        this.authorityId = authorityId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
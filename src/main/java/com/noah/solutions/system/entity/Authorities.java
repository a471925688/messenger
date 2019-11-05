package com.noah.solutions.system.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 权限表
 */
@Entity
@Table(name = "sys_authorities")
public class Authorities implements Serializable {
    private static final long serialVersionUID = -3705507499619441511L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer authorityId;  // 权限id

    private String authorityName;  // 权限名称

    private String authority;  // 权限标识（如果为空不会添加在shiro的权限列表中）

    private String menuUrl;  // 菜单url

    private Integer parentId;  // 上级菜单

    private Integer isMenu;  // 菜单还是按钮（菜单会显示在侧导航，按钮不会显示在侧导航，只要url不是空，都会作为权限标识）

    private Integer orderNumber;  // 排序号

    private String menuIcon;  // 菜单图标

    @Column(insertable = false,updatable = false)
    private Date createTime;  // 创建时间

    @Column(insertable = false)
    private Date updateTime;  // 修改时间

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Integer isMenu) {
        this.isMenu = isMenu;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
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

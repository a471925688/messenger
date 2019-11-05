package com.noah.solutions.system.entity.cart;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
//
//@Entity
//@Table(name = "tb_group")
public class Group {
    /**
     * 自增主键

     */
    @Id
    private Integer id;

    /**
     * 群号
     */
    private String groupNum;

    /**
     * 群名称
     */
    private String groupName;

    private String avatar;

    private Integer userId;

    private String buildTime;

    private String description;

    private Integer status;

    /**
     * 获取自增主键

     *
     * @return id - 自增主键

     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置自增主键

     *
     * @param id 自增主键

     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取群号
     *
     * @return group_num - 群号
     */
    public String getGroupNum() {
        return groupNum;
    }

    /**
     * 设置群号
     *
     * @param groupNum 群号
     */
    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum == null ? null : groupNum.trim();
    }

    /**
     * 获取群名称
     *
     * @return group_name - 群名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置群名称
     *
     * @param groupName 群名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * @return avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return build_time
     */
    public String getBuildTime() {
        return buildTime;
    }

    /**
     * @param buildTime
     */
    public void setBuildTime(String buildTime) {
        this.buildTime = buildTime;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * @return status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}
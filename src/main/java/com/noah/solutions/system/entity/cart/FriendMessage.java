package com.noah.solutions.system.entity.cart;

import javax.persistence.Entity;
import javax.persistence.Id;


//@Entity
public class FriendMessage {
    @Id
    private Integer id;

    /**
     * 发消息的人的id
     */
    private Integer fromUserId;

    /**
     * 收消息的人的id
     */
    private Integer toUserId;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 是否已读，1是0否
     */
    private Integer isRead;

    /**
     * 是否删除，1是0否
     */
    private Integer isDel;

    /**
     * 是否撤回，1是0否
     */
    private Integer isBack;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取发消息的人的id
     *
     * @return from_user_id - 发消息的人的id
     */
    public Integer getFromUserId() {
        return fromUserId;
    }

    /**
     * 设置发消息的人的id
     *
     * @param fromUserId 发消息的人的id
     */
    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    /**
     * 获取收消息的人的id
     *
     * @return to_user_id - 收消息的人的id
     */
    public Integer getToUserId() {
        return toUserId;
    }

    /**
     * 设置收消息的人的id
     *
     * @param toUserId 收消息的人的id
     */
    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    /**
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取发送时间
     *
     * @return send_time - 发送时间
     */
    public String getSendTime() {
        return sendTime;
    }

    /**
     * 设置发送时间
     *
     * @param sendTime 发送时间
     */
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * 获取是否已读，1是0否
     *
     * @return is_read - 是否已读，1是0否
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * 设置是否已读，1是0否
     *
     * @param isRead 是否已读，1是0否
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    /**
     * 获取是否删除，1是0否
     *
     * @return is_del - 是否删除，1是0否
     */
    public Integer getIsDel() {
        return isDel;
    }

    /**
     * 设置是否删除，1是0否
     *
     * @param isDel 是否删除，1是0否
     */
    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    /**
     * 获取是否撤回，1是0否
     *
     * @return is_back - 是否撤回，1是0否
     */
    public Integer getIsBack() {
        return isBack;
    }

    /**
     * 设置是否撤回，1是0否
     *
     * @param isBack 是否撤回，1是0否
     */
    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }
}
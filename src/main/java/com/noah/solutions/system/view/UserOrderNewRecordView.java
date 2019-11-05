package com.noah.solutions.system.view;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.entity.ReceAddres;
import com.noah.solutions.system.entity.User;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 用戶訂單最新物流
 */
@Data

public class UserOrderNewRecordView implements Serializable {
    private static final long serialVersionUID = -1820364346785639382L;
    private String  orderId;//快遞單號
    private String  orderNo;//快遞單號
    private String  title;
    private String tbExplain;
    private Date createTime;
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private User courier;

    public UserOrderNewRecordView(String orderNo,String orderId) {
        this.orderNo = orderNo;
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTbExplain() {
        return tbExplain;
    }

    public void setTbExplain(String tbExplain) {
        this.tbExplain = tbExplain;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }
}

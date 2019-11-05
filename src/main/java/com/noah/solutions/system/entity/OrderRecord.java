package com.noah.solutions.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_order_record")
@Data
public class OrderRecord implements Serializable {
    private static final long serialVersionUID = -7583506130712388649L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "BIGINT COMMENT '自增id'")
    private String id;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '标题'",nullable = false)
    private String  title;
    @Column(columnDefinition = "VARCHAR(60)  COMMENT '地点'",nullable = false)
    private String  place;
    @Column(columnDefinition = "VARCHAR(200)  COMMENT '描述'",nullable = false)
    private String tbExplain;
    @Column(columnDefinition = "INT(10)  COMMENT '处理员id(比如派送员)'")
    private String  courierId;
    @Column(columnDefinition = "INT(10)  COMMENT '操作员id'",nullable = false)
    private String  operatorId;
    @Column(columnDefinition = "INT(4)  COMMENT '当前状态(1.正常,0.异常)'",nullable = false)
    private Boolean  status;
    @Column(columnDefinition = "INT(10)  COMMENT '訂單id'")
    private String  orderId;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '发生时间'",updatable = false,insertable=false)
    private Date createTime;


    //處理員信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="courierId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private User courier;

    @Transient
    private String operatorName;
    @Transient
    private String statusStr;

    public OrderRecord(String operatorName, OrderRecord orderRecord) {
        this.operatorName = operatorName;
        this.title = orderRecord.getTitle();
        this.tbExplain = orderRecord.getTbExplain();
        this.courierId = orderRecord.getCourierId();
        this.operatorId = orderRecord.getOperatorId();
        this.status = orderRecord.getStatus();
        this.orderId = orderRecord.getOrderId();
        this.createTime = orderRecord.getCreateTime();
        this.courier = orderRecord.getCourier();
    }

    public OrderRecord(String title, String place, String tbExplain, String operatorId, Boolean status) {
        this.title = title;
        this.place = place;
        this.tbExplain = tbExplain;
        this.operatorId = operatorId;
        this.status = status;
    }
    public OrderRecord(String title, String place, String tbExplain, String operatorId, Boolean status,String orderId) {
        this.title = title;
        this.place = place;
        this.tbExplain = tbExplain;
        this.operatorId = operatorId;
        this.status = status;
        this.orderId = orderId;
    }

    public String getStatusStr() {
        if(status!=null){
            if(status){
                statusStr =  "正常";
            }else {
                statusStr =  "異常";
            }
        }
        return statusStr;
    }

    public void setStatusStr(String statusStr) {

        this.statusStr = statusStr;
    }

    public User getCourier() {
        return courier;
    }

    public void setCourier(User courier) {
        this.courier = courier;
    }

    public OrderRecord() {

    }


    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTbExplain() {
        return tbExplain;
    }

    public void setTbExplain(String tbExplain) {
        this.tbExplain = tbExplain;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

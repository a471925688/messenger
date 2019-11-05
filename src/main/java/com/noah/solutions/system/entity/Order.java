package com.noah.solutions.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 2018 11 26 lijun
 *
 */
@Entity
@Table(name = "tb_order")
@Data
public class Order implements Serializable {

    private static final long serialVersionUID = -9212386703318846972L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT COMMENT '订单id'")
    private String orderId;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '快递单号'",nullable = false)
    private String  orderNo;
    @Column(columnDefinition = "INT(10)  COMMENT '用户id'",nullable = false)
    private String  userId;
    @Column(columnDefinition = "INT(10)  COMMENT '收货地址id'",nullable = false)
    private String recId;
    @Column(columnDefinition = "INT(10)  COMMENT '司機id'")
    private String driverId;
    @Column(columnDefinition = "INT(10)  COMMENT '派送員id'")
    private String dispatcherId;
    @Column(columnDefinition = "VARCHAR(32)  COMMENT '袋子標籤號'")
    private String labelNo;
    @Column(columnDefinition = "INT(5) DEFAULT 1 COMMENT '记录类型(1.待发货，2.运输中，3.待取件，4.待派送，5.派送中,6.已完成，7.订单异常)'",insertable = false)
    private Integer  orderState;
    @Column(columnDefinition = "VARCHAR(500) COMMENT '订单说明'")
    private String  orderExplanation;
    @Column(columnDefinition = "VARCHAR(60) COMMENT '货物类型'")
    private String  goodsType;
    @Column(columnDefinition = "INT COMMENT '重量'")
    private Integer  goodsWeight;
    @Column(columnDefinition = "FLOAT COMMENT '体积'")
    private Float  goodsVolume;
    @Column(columnDefinition = "INT(10)  COMMENT '当前位置'")
    private String pickUpStationId;
    @Column(columnDefinition = "INT(10)  COMMENT '下一站'")
    private String pickUpStationIdNext;
    @Column(nullable = false,columnDefinition = "DOUBLE  COMMENT '运费'")
    private String  orderTokenMoney;
    @Column(columnDefinition = "VARCHAR(40)   COMMENT '提货码'")
    private String  pickUpCode;
    @Column(columnDefinition = "DATETIME   COMMENT '预约派送时间'")
    private Date  orderAppointment;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date  createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private Date updateTime;

    //用户信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private User user;

    //所属袋子
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="labelNo",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private Label label;

    //司機信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="driverId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private User driver;

    //派送員信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dispatcherId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private User dispatcher;

    //收件地址信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="recId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private ReceAddres receAddres;

    //下一個提貨點信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pickUpStationIdNext",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private PickUpStation pickUpStationNext;

    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    @JoinColumn(name="orderId",insertable = false,updatable = false)
    @OrderBy("orderReplyId asc ")
    private Set<OrderReply> orderReplys = new HashSet<>();




    ////////////////////////////////////不映射到数据库中的属性 start ///////////////////////////////////////////////////////
    //產品物流信息
    @Transient
    private Set<OrderRecord> orderRecords;
    //目的地
    @Transient
    private String pickUpStationNextStr;

    @Transient
    private String username;

    @Transient
    private String driverame;

    @Transient
    private Integer memberRepliesNum;
    ////////////////////////////////////不映射到数据库中的属性 end///////////////////////////////////////////////////////


    public Order(String orderId, String userId) {
        this.orderId = orderId;
        this.userId = userId;
    }

    public Order(String labelNo,String orderNo, String recId, String goodsType, Integer goodsWeight, Float goodsVolume, String orderTokenMoney) {
        this.labelNo = labelNo;
        this.orderNo = orderNo;
        this.recId = recId;
        this.goodsType = goodsType;
        this.goodsWeight = goodsWeight;
        this.goodsVolume = goodsVolume;
        this.orderTokenMoney = orderTokenMoney;
    }

    public Order() {

    }

    public String getLabelNo() {
        return labelNo;
    }

    public void setLabelNo(String labelNo) {
        this.labelNo = labelNo;
    }

    public String getDispatcherId() {
        return dispatcherId;
    }

    public void setDispatcherId(String dispatcherId) {
        this.dispatcherId = dispatcherId;
    }

    public User getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(User dispatcher) {
        this.dispatcher = dispatcher;
    }

    public PickUpStation getPickUpStationNext() {
        return pickUpStationNext;
    }

    public void setPickUpStationNext(PickUpStation pickUpStationNext) {
        this.pickUpStationNext = pickUpStationNext;
    }

    public Set<OrderRecord> getOrderRecords() {
        return orderRecords;
    }

    public void setOrderRecords(Set<OrderRecord> orderRecords) {
        this.orderRecords = orderRecords;
    }

    public String getUsername() {
        if(user!=null)
            username=user.getNickName();
        return username;
    }

    public User getDriver() {
        return driver;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public String getDriverame() {
        if(driver!=null)
            driverame=driver.getNickName();
        return driverame;
    }

    public void setDriverame(String driverame) {
        this.driverame = driverame;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getOrderExplanation() {
        return orderExplanation;
    }

    public void setOrderExplanation(String orderExplanation) {
        this.orderExplanation = orderExplanation;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public Integer getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Integer goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public Float getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(Float goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public String getPickUpStationId() {
        return pickUpStationId;
    }

    public void setPickUpStationId(String pickUpStationId) {
        this.pickUpStationId = pickUpStationId;
    }

    public String getPickUpStationIdNext() {
        return pickUpStationIdNext;
    }

    public void setPickUpStationIdNext(String pickUpStationIdNext) {
        this.pickUpStationIdNext = pickUpStationIdNext;
    }

    public String getOrderTokenMoney() {
        return orderTokenMoney;
    }

    public void setOrderTokenMoney(String orderTokenMoney) {
        this.orderTokenMoney = orderTokenMoney;
    }

    public String getPickUpCode() {
        return pickUpCode;
    }

    public void setPickUpCode(String pickUpCode) {
        this.pickUpCode = pickUpCode;
    }

    public Date getOrderAppointment() {
        return orderAppointment;
    }

    public void setOrderAppointment(Date orderAppointment) {
        this.orderAppointment = orderAppointment;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReceAddres getReceAddres() {
        return receAddres;
    }

    public void setReceAddres(ReceAddres receAddres) {
        this.receAddres = receAddres;
    }

    public String getPickUpStationNextStr() {
        if(pickUpStationNext!=null){
            pickUpStationNextStr = pickUpStationNext.getPickUpStationName();
        }
        return pickUpStationNextStr;
    }

    public void setPickUpStationNextStr(String pickUpStationNextStr) {
        this.pickUpStationNextStr = pickUpStationNextStr;
    }


    public Integer getMemberRepliesNum() {
        if(orderReplys.size()>0){
            memberRepliesNum = 0;
            for (OrderReply o:orderReplys) {
                if(!o.getIsRead()&&o.getIsMember())
                    memberRepliesNum++;
            }
        }
        return memberRepliesNum;
    }

    public void setMemberRepliesNum(Integer memberRepliesNum) {
        this.memberRepliesNum = memberRepliesNum;
    }

    public Set<OrderReply> getOrderReplys() {
        return orderReplys;
    }

    public void setOrderReplys(Set<OrderReply> orderReplys) {
        this.orderReplys = orderReplys;
    }
}

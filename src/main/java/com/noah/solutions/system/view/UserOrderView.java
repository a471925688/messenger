package com.noah.solutions.system.view;

import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.entity.ReceAddres;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserOrderView implements Serializable {
    private static final long serialVersionUID = -2683494742921650722L;
    private String orderId;//訂單號
    private String  orderNo;//快遞單號
    private String  labelNo;//标签号
    private String  goodsType;//產品類型
    private Integer  goodsWeight;//產品種類
    private float  goodsVolume;//產品體積
    private String  orderTokenMoney;//運費
    private String  pickUpCode;//提貨碼
    private ReceAddres receAddres;//收貨地址
    private Set<OrderRecord> orderRecords;//訂單物流信息
    private Integer orderState;//訂單狀態
    private String orderStateStr;//訂單狀態

    public UserOrderView() {
    }

    public UserOrderView(String orderId,String labelNo, String orderNo, String goodsType, Integer goodsWeight, float goodsVolume, String orderTokenMoney, String pickUpCode, Integer orderState, ReceAddres receAddres) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.labelNo = labelNo;
        this.goodsType = goodsType;
        this.goodsWeight = goodsWeight;
        this.goodsVolume = goodsVolume;
        this.orderTokenMoney = orderTokenMoney;
        this.pickUpCode = pickUpCode;
        this.receAddres = receAddres;
        this.orderState = orderState;
    }

    public UserOrderView(String orderId, String labelNo,String orderNo, String goodsType, Integer goodsWeight, float goodsVolume, String orderTokenMoney, String pickUpCode,Integer orderState, ReceAddres receAddres,Set<OrderRecord> orderRecords) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.labelNo = labelNo;
        this.goodsType = goodsType;
        this.goodsWeight = goodsWeight;
        this.goodsVolume = goodsVolume;
        this.orderTokenMoney = orderTokenMoney;
        this.pickUpCode = pickUpCode;
        this.receAddres = receAddres;
        this.orderState = orderState;
        this.orderRecords = orderRecords;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public String getOrderStateStr() {
        if(!StringUtils.isEmpty(orderState)){
            orderStateStr = ProjectConfig.OrderState.queryValue(orderState);
        }
        return orderStateStr;
    }

    public void setOrderStateStr(String orderStateStr) {
        this.orderStateStr = orderStateStr;
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

    public float getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(float goodsVolume) {
        this.goodsVolume = goodsVolume;
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

    public ReceAddres getReceAddres() {
        return receAddres;
    }

    public void setReceAddres(ReceAddres receAddres) {
        this.receAddres = receAddres;
    }

    public Set<OrderRecord> getOrderRecords() {
        return orderRecords;
    }

    public void setOrderRecords(Set<OrderRecord> orderRecords) {
        this.orderRecords = orderRecords;
    }
}

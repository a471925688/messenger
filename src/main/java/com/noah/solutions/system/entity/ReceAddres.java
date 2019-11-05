package com.noah.solutions.system.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah.solutions.common.ProjectCache;
import com.noah.solutions.common.ProjectConfig;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * 收货地址
 */
@Entity
@Data
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
@Where(clause = "is_delete=0")
public class ReceAddres implements Serializable {
    private static final long serialVersionUID = -1562911814195788191L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '收货信息id'")
    private String recId;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(收货人姓名)'")
    private String recName;
    @Column(columnDefinition = "VARCHAR(30) NOT NULL COMMENT '(收货人电话)'")
    private String recPhone;
    @Column(columnDefinition = "INT(10)  COMMENT '地址地區'")
    private String  placeId;
    @Column(columnDefinition = "INT(10)  COMMENT '提货点id'")
    private String pickUpStationId;
    @Column(columnDefinition = "VARCHAR(200) NOT NULL COMMENT '详细地址'")
    private String  recDetailedAddr;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(备注)'")
    private String recRemarks;
    @Column(columnDefinition = "INT(10)  COMMENT '关联的会员id'",nullable = false)
    private String userId;
    @Column(columnDefinition = "INT(10)  COMMENT '收货方式'")
    private Integer recMode;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date  recCreateTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private Date recUpdateTime;
    @Column(columnDefinition = "INT(4)   DEFAULT 0  COMMENT '是否刪除'",nullable = false,insertable = false)
    private Boolean isDelete;

    @Transient
    private String recModeStr;

    @Transient
    private String recDetailedAddrStr;

    //收件地址信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pickUpStationId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private PickUpStation pickUpStation;

    //收件地址信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="placeId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private Place place;



    public ReceAddres() {

    }

    public ReceAddres(String recName, String recPhone, String placeId, String pickUpStationId, String recDetailedAddr, String recRemarks,String userId) {
        this.recName = recName;
        this.recPhone = recPhone;
        this.placeId = placeId;
        this.pickUpStationId = pickUpStationId;
        this.recDetailedAddr = recDetailedAddr;
        this.recRemarks = recRemarks;
        this.userId = userId;
    }
    public ReceAddres(String recId,String recName, String recPhone, String placeId, String pickUpStationId, String recDetailedAddr, String recRemarks,String userId) {
        this.recId = recId;
        this.recName = recName;
        this.recPhone = recPhone;
        this.placeId = placeId;
        this.pickUpStationId = pickUpStationId;
        this.recDetailedAddr = recDetailedAddr;
        this.recRemarks = recRemarks;
        this.userId = userId;
    }
    public String getRecModeStr() {
        if(!StringUtils.isEmpty(recMode))
            recModeStr = ProjectConfig.ReceAddresMode.queryValue(recMode);
        return recModeStr;
    }


    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isdelete) {
        isDelete = isdelete;
    }

    public String getRecId() {
        return recId;
    }

    public void setRecId(String recId) {
        this.recId = recId;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecPhone() {
        return recPhone;
    }

    public void setRecPhone(String recPhone) {
        this.recPhone = recPhone;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPickUpStationId() {
        return pickUpStationId;
    }

    public void setPickUpStationId(String pickUpStationId) {
        this.pickUpStationId = pickUpStationId;
    }

    public String getRecDetailedAddr() {
        return recDetailedAddr;
    }

    public void setRecDetailedAddr(String recDetailedAddr) {
        this.recDetailedAddr = recDetailedAddr;
    }

    public String getRecRemarks() {
        return recRemarks;
    }

    public void setRecRemarks(String recRemarks) {
        this.recRemarks = recRemarks;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRecMode() {
        return recMode;
    }

    public void setRecMode(Integer recMode) {
        this.recMode = recMode;
    }

    public Date getRecCreateTime() {
        return recCreateTime;
    }

    public void setRecCreateTime(Date recCreateTime) {
        this.recCreateTime = recCreateTime;
    }

    public Date getRecUpdateTime() {
        return recUpdateTime;
    }

    public void setRecUpdateTime(Date recUpdateTime) {
        this.recUpdateTime = recUpdateTime;
    }

    public void setRecModeStr(String recModeStr) {
        this.recModeStr = recModeStr;
    }

    public PickUpStation getPickUpStation() {
        return pickUpStation;
    }

    public void setPickUpStation(PickUpStation pickUpStation) {
        this.pickUpStation = pickUpStation;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }


    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getRecDetailedAddrStr() {
        if(recMode!=null){
            if(recMode.equals(ProjectConfig.ReceAddresMode.DELIVERYABOVE.getValue())&&place!=null){
                recDetailedAddrStr = ProjectCache.getTransferStation().getAddress()+"轉->"+place.getDetails()+"-"+recDetailedAddr+"("+recId+")";
            }else if(pickUpStation!=null){
                recDetailedAddrStr = ProjectCache.getTransferStation().getAddress()+"轉->中轉站:"+pickUpStation.getPickUpStationName()+"("+recId+")";
            }
        }
        return recDetailedAddrStr;
    }

    public void setRecDetailedAddrStr(String recDetailedAddrStr) {
        this.recDetailedAddrStr = recDetailedAddrStr;
    }
}

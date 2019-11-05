package com.noah.solutions.system.entity;


import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 提货站点
 */
@Entity
@Where(clause = "is_delete=0")
public class PickUpStation implements Serializable {
    private static final long serialVersionUID = -3193406244825750498L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '提货点id'")
    private String pickUpStationId;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '(店名)'")
    private String pickUpStationName;
    @Column(columnDefinition = "VARCHAR(30) NOT NULL COMMENT '(电话)'")
    private String pickUpStationPhone;
    @Column(columnDefinition = "INT(10)  COMMENT '经度'")
    private Float pointx;
    @Column(columnDefinition = "INT(10)  COMMENT '维度'")
    private Float pointy;
    @Column(columnDefinition = "VARCHAR(500) NOT NULL COMMENT '详细地址'")
    private String  pickUpStationAddr;
    @Column(columnDefinition = "VARCHAR(100)  COMMENT '(备注)'")
    private String recRemarks;

    @Column(columnDefinition = "INT(4) DEFAULT 0  COMMENT '(是否刪除)'")
    private Integer isDelete;

    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;  // 注册时间

    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '修改时间'",insertable=false)
    private Date updateTime;  // 修改时间

    public PickUpStation() {
    }


    public PickUpStation(String pickUpStationName, String pickUpStationPhone, Float pointx, Float pointy, String pickUpStationAddr, String recRemarks) {
        this.pickUpStationName = pickUpStationName;
        this.pickUpStationPhone = pickUpStationPhone;
        this.pointx = pointx;
        this.pointy = pointy;
        this.pickUpStationAddr = pickUpStationAddr;
        this.recRemarks = recRemarks;
    }


    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public String getPickUpStationId() {
        return pickUpStationId;
    }

    public void setPickUpStationId(String pickUpStationId) {
        this.pickUpStationId = pickUpStationId;
    }

    public String getPickUpStationName() {
        return pickUpStationName;
    }

    public void setPickUpStationName(String pickUpStationName) {
        this.pickUpStationName = pickUpStationName;
    }

    public String getPickUpStationPhone() {
        return pickUpStationPhone;
    }

    public void setPickUpStationPhone(String pickUpStationPhone) {
        this.pickUpStationPhone = pickUpStationPhone;
    }

    public Float getPointx() {
        return pointx;
    }

    public void setPointx(Float pointx) {
        this.pointx = pointx;
    }

    public Float getPointy() {
        return pointy;
    }

    public void setPointy(Float pointy) {
        this.pointy = pointy;
    }

    public String getPickUpStationAddr() {
        return pickUpStationAddr;
    }

    public void setPickUpStationAddr(String pickUpStationAddr) {
        this.pickUpStationAddr = pickUpStationAddr;
    }

    public String getRecRemarks() {
        return recRemarks;
    }

    public void setRecRemarks(String recRemarks) {
        this.recRemarks = recRemarks;
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

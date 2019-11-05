package com.noah.solutions.system.entity;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.utils.StringUtil;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

//

@Entity
@Data
public class Staff implements Serializable {
    private static final long serialVersionUID = -2263121414653302660L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10) AUTO_INCREMENT NOT NULL COMMENT '自增ID'")
    private Integer  staffId;
    @Column(columnDefinition = "INT NOT NULL COMMENT '员工类型(1.操作员，2.快递员)'")
    private Integer staffType;
    @Column(insertable = false,updatable = false)
    private Integer userId;
    @Column(columnDefinition = "INT(10)  COMMENT '部门(提货点)id'",nullable = false)
    private String pickUpStationId;

    //工作人员信息
    @OneToOne(fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="userId")
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private User user;

    //提货点信息
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="pickUpStationId",insertable = false,updatable = false)
    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
    private PickUpStation pickUpStation;


    @Transient
    private String staffTypeQuery;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getStaffType() {
        return staffType;
    }

    public void setStaffType(Integer staffType) {
        this.staffType = staffType;
    }

    public String getPickUpStationId() {
        return pickUpStationId;
    }

    public void setPickUpStationId(String pickUpStationId) {
        this.pickUpStationId = pickUpStationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PickUpStation getPickUpStation() {
        return pickUpStation;
    }

    public void setPickUpStation(PickUpStation pickUpStation) {
        this.pickUpStation = pickUpStation;
    }

    public String getStaffTypeQuery() {
        if(staffType!=null){
            staffTypeQuery = ProjectConfig.StaffType.OPERATOR.queryValue(staffType);
        }
        return staffTypeQuery;
    }

    public void setStaffTypeQuery(String staffTypeQuery) {
        this.staffTypeQuery = staffTypeQuery;
    }
}
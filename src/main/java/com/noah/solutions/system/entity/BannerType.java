package com.noah.solutions.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class BannerType implements Serializable {
    private static final long serialVersionUID = -3388486360505063829L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '自增id'")
    private String typeId;
    @Column(columnDefinition = "DOUBLE COMMENT '圖片大小比列'")
    private String  imgRatio;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '類型名稱'")
    private String  name;
    @Column(columnDefinition = "INT COMMENT '最大條數'")
    private Integer  maxNumber;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '類型說明'")
    private String  explanation;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private Date updateTime;

    //詳情圖片
//    @Where(clause="group_id = 1")
//    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
//    @JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
//    @JoinColumn(name="groupId",insertable = false,updatable = false)
//    @OrderBy("sort ASC")
//    private Set<TbImage> detailsImgNames = new HashSet<>();

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getImgRatio() {
        return imgRatio;
    }

    public void setImgRatio(String imgRatio) {
        this.imgRatio = imgRatio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(Integer maxNumber) {
        this.maxNumber = maxNumber;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
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

package com.noah.solutions.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.noah.solutions.common.ProjectConfig;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Banner implements Serializable {
    private static final long serialVersionUID = -3388486360505063829L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10)  AUTO_INCREMENT NOT NULL COMMENT '自增id'")
    private String id;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '標題'")
    private String  title;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '內容'")
    private String  content;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '图片'")
    private String  imageName;
    @Column(columnDefinition = "INT COMMENT '類型id'")
    private String  typeId;
    @Column(columnDefinition = "VARCHAR(200) COMMENT '跳轉地址'")
    private String  url;
    @Column(columnDefinition = "INT COMMENT '排序'")
    private Integer  sort;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private Date updateTime;

    @Transient
    private String imageUrl;


    public String getImageUrl() {
        if(!StringUtils.isEmpty(imageName))
            imageUrl = ProjectConfig.PROJECT_PATH+ "image/"+imageName;
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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

//    public Set<TbImage> getDetailsImgNames() {
//        return detailsImgNames;
//    }

//    public void setDetailsImgNames(Set<TbImage> detailsImgNames) {
//        this.detailsImgNames = detailsImgNames;
//    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}


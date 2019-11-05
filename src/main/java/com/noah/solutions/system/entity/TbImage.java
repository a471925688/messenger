package com.noah.solutions.system.entity;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name ="tb_image",
        indexes = {@Index(columnList = "type"),@Index(columnList = "groupId"),@Index(columnList = "groupType")} )
@DynamicUpdate
public class TbImage implements Serializable {
    private static final long serialVersionUID = 8737397365640763584L;
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10) AUTO_INCREMENT NOT NULL COMMENT '图片ID'")
    private String  id;
    @Column(columnDefinition = "VARCHAR(300)  COMMENT '图片名稱'")
    private String name;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '图片地址'")
    private String path;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '圖片點擊的跳轉連接'")
    private String url;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '縮略圖地址'")
    private String thumbnailsPath;
    @Column(columnDefinition = "INT(1) COMMENT '图片类型（1.主图，2附图）'")
    private Integer  type;
    @Column(columnDefinition = "INT(10) COMMENT '所属组的id'")
    private String  groupId;
    @Column(columnDefinition = "INT(2)  COMMENT '所属组的类型(1.廣告)'",updatable = false)
    private Integer  groupType;
    @Column(columnDefinition = "INT(10) COMMENT '排序'")
    private String  sort;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '更新时间'",insertable=false)
    private Date updateTime;


    public TbImage() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailsPath() {
        return thumbnailsPath;
    }

    public void setThumbnailsPath(String thumbnailsPath) {
        this.thumbnailsPath = thumbnailsPath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
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
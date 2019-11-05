package com.noah.solutions.system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 2019 6 12 lijun
 * 信差地址地区信息
 */
@Entity
@Table(name ="place",
        indexes = {@Index(columnList = "parentId"),@Index(columnList = "level")} )
@Data
public class Place implements Serializable {
    private static final long serialVersionUID = 1328657319701220342L;
    @Id
    private String  id;
    private String  name;
    private String  parentId;
    private Integer  level;
    private String  details;

    @Transient
    private List<Place> child = new ArrayList<>();

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<Place> getChild() {
        return child;
    }

    public void setChild(List<Place> child) {
        this.child = child;
    }
}

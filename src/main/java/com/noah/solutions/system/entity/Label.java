package com.noah.solutions.system.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Where(clause = "is_delete=0")
public class Label implements Serializable {
    private static final long serialVersionUID = 8109065967658186637L;
    @Id
    @Column(columnDefinition = "VARCHAR(32) NOT NULL COMMENT '標籤號'",unique = true, nullable = false)
    private String no;
    @Column(columnDefinition = "VARCHAR(100) NOT NULL COMMENT '備註'",nullable = false)
    private String remarks;
    @Column(columnDefinition = "INT DEFAULT 1 COMMENT '狀態1.庫存,2.已出庫'",insertable = false)
    private Integer status;
    @Column(columnDefinition = "INT(4) DEFAULT 0  COMMENT '(是否刪除)'",insertable = false)
    private Integer isDelete;
    @Column(columnDefinition = "DATETIME  DEFAULT NOW() COMMENT '创建时间'",updatable = false,insertable=false)
    private Date createTime;

    public Label(String no) {
        this.no = no;
    }

    public Label() {
    }

    public String getNo() {
        return no;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setNo(String no) {
        this.no = no;
    }
}

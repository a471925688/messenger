package com.noah.solutions.system.entity;

import javax.persistence.*;
import java.io.Serializable;

//
//@Entity(name = "tb_captcha")
//@Table(appliesTo = "tb_captcha",comment = "文件表")
//@Data
@Entity
@Table(name ="tb_captcha",
        indexes = {@Index(columnList = "captchaPhone"),@Index(columnList = "captchaData")} )
public class Captcha implements Serializable {
    private static final long serialVersionUID = 1545673769439839745L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false,columnDefinition = "INT(10) AUTO_INCREMENT NOT NULL COMMENT '自增ID'")
    private Integer  captchaId;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '電話號碼'")
    private String captchaPhone;
    @Column(columnDefinition = "VARCHAR(300) NOT NULL COMMENT '驗證碼'")
    private String captchaData;
    @Column(columnDefinition = "BIGINT  COMMENT '過期時間'",nullable = false)
    private Long  captchaExpirationDate;


    public Captcha() {
    }

    public Captcha(String captchaPhone, String captchaData, Long captchaExpirationDate) {
        this.captchaPhone = captchaPhone;
        this.captchaData = captchaData;
        this.captchaExpirationDate = captchaExpirationDate;
    }

    public Integer getCaptchaId() {
        return captchaId;
    }

    public void setCaptchaId(Integer captchaId) {
        this.captchaId = captchaId;
    }

    public String getCaptchaPhone() {
        return captchaPhone;
    }

    public void setCaptchaPhone(String captchaPhone) {
        this.captchaPhone = captchaPhone;
    }

    public String getCaptchaData() {
        return captchaData;
    }

    public void setCaptchaData(String captchaData) {
        this.captchaData = captchaData;
    }

    public Long getCaptchaExpirationDate() {
        return captchaExpirationDate;
    }

    public void setCaptchaExpirationDate(Long captchaExpirationDate) {
        this.captchaExpirationDate = captchaExpirationDate;
    }
}
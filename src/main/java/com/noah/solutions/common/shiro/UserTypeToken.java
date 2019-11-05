package com.noah.solutions.common.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserTypeToken extends UsernamePasswordToken {
    private Integer userType;

    public UserTypeToken(String username, String password, Integer userType) {
        super(username, password);
        this.userType = userType;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }
}

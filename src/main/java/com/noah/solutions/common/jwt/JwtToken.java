package com.noah.solutions.common.jwt;

import org.apache.shiro.authc.UsernamePasswordToken;

public class JwtToken extends UsernamePasswordToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }
    public JwtToken(String username, String password) {
        super(username, (char[])(password != null ? password.toCharArray() : null), false, (String)null);
        this.token = JwtUtil.sign(username,password);
    }

    public Object getToken() {
        return token;
    }
//
//    @Override
//    public Object getCredentials() {
//        return token;
//    }
}
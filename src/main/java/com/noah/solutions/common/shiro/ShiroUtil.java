package com.noah.solutions.common.shiro;

import com.noah.solutions.system.entity.User;
import org.apache.shiro.SecurityUtils;

public class ShiroUtil {
    public static User getUser(){
        User user = null;
        Object o = SecurityUtils.getSubject().getPrincipal();
        if(o!=null){

        }
        user= (User) o;
        return user;
    }

    public static Integer getUserId(){
        Integer  userId = null;
        User user = getUser();
        if(user!=null)
            userId = user.getUserId();
        return userId;
    }



    /**
     * 获取当前登录的username
     */
    public static String getUserName() {
        User loginUser = getUser();
        return loginUser == null ? null : loginUser.getUsername();
    }


    /**
     * 获取当前登录的username
     */
    public static Integer getUserType() {
        User loginUser = getUser();
        return loginUser == null ? null : loginUser.getType();
    }
}

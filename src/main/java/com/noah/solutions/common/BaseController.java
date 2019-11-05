package com.noah.solutions.common;

import com.noah.solutions.system.entity.User;
import org.apache.shiro.SecurityUtils;

/**
 * Controller基类
 * Created by wangfan on 2018-02-22 上午 11:29
 */
public class BaseController {

//    /**
//     * 获取当前登录的user
//     */
//    public User getLoginUser() {
//        Subject subject = SecurityUtils.getSubject();
//        User user = null;
//        if (subject != null) {
//            Object object = subject.getPrincipal();
//            if (object != null) {
//                user = new User();
//                BeanUtils.copyProperties(object,user);
//            }
//        }
//        return user;
//    }
//
//    /**
//     * 获取当前登录的userId
//     */
//    public Integer getLoginUserId() {
//
//        User loginUser = getLoginUser();
//        return loginUser == null ? null : loginUser.getUserId();
//    }

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
    public String getUserName() {
        User loginUser = getUser();
        return loginUser == null ? null : loginUser.getUsername();
    }


    /**
     * 获取当前登录的username
     */
    public Integer getUserType() {
        User loginUser = getUser();
        return loginUser == null ? null : loginUser.getType();
    }

    /**
     * 登出
     */
    public void loginOut() {
        SecurityUtils.getSubject().logout();
    }


}

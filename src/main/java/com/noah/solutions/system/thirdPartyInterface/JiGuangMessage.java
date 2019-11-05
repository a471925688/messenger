package com.noah.solutions.system.thirdPartyInterface;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class JiGuangMessage {
    // 设置好账号的app_key和masterSecret是必须的
    private static String APP_KEY = "95891f17f21331a7fda4c679";
    private static String MASTER_SECRET = "0132042666a7348c7a58892c";
    private static Boolean  PRODUCTION = false;

    @Value("${jiguang.app_key}")
    public  void setAppKey(String appKey) {
        APP_KEY = appKey;
    }
    @Value("${jiguang.master_secret}")
    public  void setMasterSecret(String masterSecret) {
        MASTER_SECRET = masterSecret;
    }
    @Value("${jiguang.production}")
    public void setPRODUCTION(Boolean PRODUCTION) {
        JiGuangMessage.PRODUCTION = PRODUCTION;
    }

    public static Boolean getPRODUCTION() {
        return PRODUCTION;
    }


    //极光推送>>Android
    //Map<String, String>

//    public static void main(String[] args) {
//        Map m = new HashMap();
//        m.put("msg","121231-3333");
//        m.put("id","191e35f7e020dc12f41");
//        m.put("title","信差推送");
//        jpushAll(m);
//    }
    public static void jpushAndroid(Map<String, String> parm) {

        //创建JPushClient(极光推送的实例)
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        //推送的关键,构造一个payload
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())//指定android平台的用户
                .setAudience(Audience.all())//你项目中的所有用户
                //.setAudience(Audience.registrationId(parm.get("id")))//registrationId指定用户
                .setNotification(Notification.android(parm.get("msg"), parm.get("title"), parm))
                //发送内容
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                //这里是指定开发环境,不用设置也没关系
                .setMessage(Message.content(parm.get("msg")))//自定义信息
                .build();
        try {
            PushResult pu = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    //极光推送>>ios
    //Map<String, String>
    public static  void jpushIOS(Map<String, String> param) {
        //创建JPushClient
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())//ios平台的用户
//                .setAudience(Audience.all())//所有用户
                .setAudience(Audience.registrationId(param.get("id")))//registrationId指定用户
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(param.get("msg"))
                                .setBadge(+1)
                                .setSound("happy")//这里是设置提示音(更多可以去官网看看)
                                .addExtras(param)
                                .build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(false).build())
                .setMessage(Message.newBuilder().setMsgContent(param.get("msg")).setTitle(param.get("title")).addExtras(param).build())//自定义信息
                .build();

        try {
            PushResult pu = jpushClient.sendPush(payload);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
    }

    //极光推送>>All所有平台
    public static Boolean jpushAll(Map<String, String> param) {
        //创建JPushClient
        JPushClient jpushClient = new JPushClient(MASTER_SECRET, APP_KEY);
        //创建option
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())  //所有平台的用户
//                .setAudience(Audience.all())//registrationId指定用户
                .setAudience(Audience.registrationId(param.get("id")))//registrationId指定用户
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder() //发送ios
                                .setAlert(param.get("msg")) //消息体
                                .setBadge(+1)
                                .setSound("happy") //ios提示音
                                .addExtras(param) //附加参数
                                .build())
                        .addPlatformNotification(AndroidNotification.newBuilder() //发送android
                                .addExtras(param) //附加参数
                                .setAlert(param.get("msg")) //消息体
                                .setTitle(param.get("title"))
                                .build())
                        .build())
                .setOptions(Options.newBuilder().setApnsProduction(PRODUCTION).build())//指定开发环境 true为生产模式 false 为测试模式 (android不区分模式,ios区分模式)
                .setMessage(Message.newBuilder().setMsgContent(param.get("msg")).setTitle(param.get("title")).addExtras(param).build())//自定义信息
                .build();
        try {
            PushResult pu = jpushClient.sendPush(payload);
            System.out.println(pu.toString());
            return true;
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return false;
    }

}

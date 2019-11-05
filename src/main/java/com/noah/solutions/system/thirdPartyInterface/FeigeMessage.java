package com.noah.solutions.system.thirdPartyInterface;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.utils.HttpClientUtil;

public class FeigeMessage {
    private static final String sendUrl="https://www.mdtechcorp.com/openapi/";//短信發送url
    private static String username="91920861";//用戶名
    private static String password="9esus!@#$";
    private static Integer returnMode = 1;
    private static Integer type = 1;

    /**
     * 發送短信驗證碼
     * @param data 內容
     * @param mobile 接收方電話
     * @return
     */
    public static Boolean sendCode(String data,String mobile ){
        JSONObject param = new JSONObject().fluentPut("destinatingAddress",mobile).fluentPut("username",username)
                .fluentPut("password",password).fluentPut("SMS","您的驗證碼為:"+data).fluentPut("returnMode",returnMode).fluentPut("type",type);
        if(Integer.valueOf(HttpClientUtil.doPost(sendUrl,param).trim())>=0)
            return true;
        return false;
    }
//    public static void main(String[] args) {
//        System.out.println(sendCode("21221","8617390326063"));
//    }

}

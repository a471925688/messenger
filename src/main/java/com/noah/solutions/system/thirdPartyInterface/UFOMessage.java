package com.noah.solutions.system.thirdPartyInterface;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.utils.HttpClientUtil;

import java.util.ArrayList;
import java.util.List;

public class UFOMessage {
    private static final String sendUrl="https://api3.ufosend.com/v3.0/sms_connect.php";//短信發送url
    private static String account_name="noah@u3.ufosend.com";//用戶名
    private static String api_key="b34af8b6c42a5ba6";
    private static Integer returnMode = 1;
    private static Integer type = 1;

    /**
     * 發送短信驗證碼
     * @param data 內容
     * @param mobile 接收方電話
     * @return
     */
    public static Object sendCode(String data,String mobile ){
        String sms_content = "";
        if(mobile.startsWith("86.")){
            sms_content = "【信差】您的验证码：$password$，有效时间5分钟，如果不是本人操作，请忽略此信息";
        }else {
            sms_content = "【信差】您的驗證碼：$password$，有效時間5分鐘，如果不是本人操作，請忽略此信息";
        }
        JSONObject meta_json = new JSONObject().fluentPut("sender","")
                .fluentPut("sms_content",sms_content)
                .fluentPut("send_also_ofca_registers",1)
                .fluentPut("response_url","http://w86p6p.natappfree.cc/distribution/text/callback");
        List user_json = new ArrayList();
        user_json.add(new JSONObject().fluentPut("mobile",mobile).fluentPut("password",data));
        JSONObject param = new JSONObject().fluentPut("account_name",account_name).fluentPut("api_key",api_key).fluentPut("meta_json",meta_json).fluentPut("user_json",user_json);
        System.out.println("param:"+param.toJSONString());
        return HttpClientUtil.doPost(sendUrl,param);
//        if(Integer.valueOf(HttpClientUtil.doPost(sendUrl,param).trim())>=0)
//            return true;
//        return false;
    }
    public static void main(String[] args) {
        System.out.println(sendCode("526536","852.91920861"));
    }

}

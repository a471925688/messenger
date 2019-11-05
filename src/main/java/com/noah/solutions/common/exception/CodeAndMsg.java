package com.noah.solutions.common.exception;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;

/**
 * 2018 12 1 lijun
 * 枚举类（存放常量属性）
 */
public enum CodeAndMsg  implements IExceptionEnums {
    SUCESS(0,"OK"),
    ERROR(1,"平台异常"),
    GOTO_INDEX(111,"跳轉到主頁"),
    ERROR_TIME_OUT(111,"請求頁面超時"),
    ERROR_CHECK_USER(401,"用戶驗證失敗"),
    TIMEOUT_TOKEN(401,"登录过期，请重新登录"),
    PARAM_ERROR(400,"參數錯誤"),
    NOTFIND_PAGE(404,"找不到頁面"),
    HANDLESUCESS(0,"操作成功"),
    ADDSUCESS(0,"添加成功"),
    EDITSUCESS(0,"编辑成功"),
    DELSUCESS(0,"删除成功"),
    USER_LOGIN_SUCESS(0,"登录成功"),
    SUCESSUPLOAD(0,"文件上传成功"),
    ERRORUPLOAD(1,"文件上传失败"),
    USER_ADDERROR(1001,"註冊用戶失败(账号或手機號已存在)"),
    USER_EDITERROR(1001,"編輯失敗,用戶名或手機號已存在"),
    USER_NOTFINDUSERNAME(1002,"用户不存在"),
    USER_PASSWORDERROR(1003,"密码错误"),
    USER_PHONEISNOT(1001,"手機號不能為空"),
    USER_GETCAPTCHAERROR(1001,"獲取驗證碼失敗(手機號已註冊)"),
    USER_CAPTCHAERROR(1101,"验证码错误!"),
    USER_CAPTCHATIMEOUT(1101,"验证码已過期!"),
    USER_BANNED(1004,"账号被禁用"),
    DISCOUNTCODE_ADDERROR(1101,"新增優惠碼失敗(優惠碼已存在)"),
    ORDER_REFUND(1201,"自動退款失敗,請手動處理"),
    BRAND_ADDERROR(2001,"新增品牌失敗(品牌名已存在)"),
    COLOR_ADDERROR(2101,"新增顏色失敗(顏色名稱已存在)"),
    FILE_PDF_NOTFIND(3001,"郵件發送失敗PDF模板不存在");




    public int code;
    public String message;

    private CodeAndMsg(int code, String message){
        this.code = code;
        this.message = message;
    }


    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return message;
    }

    @Override
    public JSONObject getJSON() {
        return new JSONObject().fluentPut("code", code)
                .fluentPut("msg",message);
    }

    @Override
    public JSONObject getJSON(Object data) {
        return new JSONObject().fluentPut("code", code)
                .fluentPut("msg",message)
                .fluentPut("data",data);
    }

    @Override
    public JSONObject pageData(Page page) {
        if(page!=null){
            return new JSONObject().fluentPut("code", code)
                    .fluentPut("msg",message)
                    .fluentPut("count",page.getTotalElements())
                    .fluentPut("data",page.getContent());
        }else {
            return getJSON();
        }
    }
}

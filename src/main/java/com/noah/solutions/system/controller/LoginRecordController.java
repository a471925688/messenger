package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.utils.StringUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.noah.solutions.system.service.LoginRecordService;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登录日志管理
 **/
@Controller
@RequestMapping("/system/loginRecord")
public class LoginRecordController {
    @Autowired
    private LoginRecordService loginRecordService;

    @RequiresPermissions("loginRecord:view")
    @RequestMapping()
    public String loginRecord() {
        return "system/login_record.html";
    }

    /**
     * 查询所有登录日志
     **/
    @RequiresPermissions("loginRecord:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(Integer page, Integer limit, String startDate, String endDate, String account) {
        if (StringUtil.isBlank(startDate)) {
            startDate = null;
        } else {
            startDate += " 00:00:00";
        }
        if (StringUtil.isBlank(endDate)) {
            endDate = null;
        } else {
            endDate += " 23:59:59";
        }
        if (StringUtil.isBlank(account)) {
            account = null;
        }
        return CodeAndMsg.SUCESS.pageData(loginRecordService.list(page, limit, startDate, endDate, account));
    }

}

package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.jwt.JwtUtil;
import com.noah.solutions.common.shiro.ShiroSessionManager;
import com.noah.solutions.common.utils.CookiesUtil;
import com.noah.solutions.common.utils.StringUtil;
import com.noah.solutions.common.utils.UserAgentGetter;
import com.noah.solutions.system.entity.Authorities;
import com.noah.solutions.system.entity.LoginRecord;
import com.noah.solutions.system.service.AuthoritiesService;
import com.noah.solutions.system.service.LoginRecordService;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MainController
 */
@Controller
@RequestMapping("/app/main")
public class AppMainController extends BaseController {

    /**
     * 收费标准
     */
    @RequestMapping("/charge")
    public String charge() {
        return "app/charge.html";
    }

    /**
     * 关于我们
     */
    @RequestMapping("/aboutus")
    public String aboutus() {
        return "app/aboutus.html";
    }

    /**
     * 用戶協議
     */
    @RequestMapping("/agreement")
    public String agreement() {
        return "app/agreement.html";
    }

}

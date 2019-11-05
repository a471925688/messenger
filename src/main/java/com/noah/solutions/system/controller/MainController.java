package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.common.jwt.JwtUtil;
import com.noah.solutions.common.shiro.ShiroSessionManager;
import com.noah.solutions.common.utils.CookiesUtil;
import com.noah.solutions.common.utils.DateUtil;
import com.wf.captcha.utils.CaptchaUtil;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.utils.StringUtil;
import com.noah.solutions.common.utils.UserAgentGetter;
import com.noah.solutions.system.entity.Authorities;
import com.noah.solutions.system.entity.LoginRecord;
import com.noah.solutions.system.service.AuthoritiesService;
import com.noah.solutions.system.service.LoginRecordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.http.client.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
public class MainController extends BaseController implements ErrorController {
    @Autowired
    private AuthoritiesService authoritiesService;
    @Autowired
    private LoginRecordService loginRecordService;

    /**
     * 主页
     */
    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        List<Authorities> authorities = authoritiesService.listByUserId(getUserId());
        List<Map<String, Object>> menuTree = getMenuTree(authorities, -1);
        model.addAttribute("menus", menuTree);
        model.addAttribute("login_user", getUser());
        return "index.html";
    }


    /**
     * 登录页
     */
    @GetMapping("/login")
    public String login() {
        if (getUser() != null) {
            return "redirect:index";
        }
        return "login.html";
    }

    /**
     * 登录
     */
    @ResponseBody
    @PostMapping("/login")
    @ApiOperation(value = "查询所有菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "code", value = "验证码", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject doLogin(String username, String password, String code, HttpServletRequest request,HttpServletResponse response, Session session) {
        if (StringUtil.isBlank(username, password)) {
            return CodeAndMsg.PARAM_ERROR.getJSON();
        }
        try {

            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
            SecurityUtils.getSubject().login(usernamePasswordToken);
            addLoginRecord(getUserId(), request);
            CookiesUtil.setCookie(response, ShiroSessionManager.getAUTHORIZATION(),SecurityUtils.getSubject().getSession().getId().toString(), 60*60*24*15);
            return CodeAndMsg.SUCESS.getJSON(SecurityUtils.getSubject().getSession().getId());
        } catch (IncorrectCredentialsException ice) {
            return CodeAndMsg.USER_PASSWORDERROR.getJSON();
        } catch (UnknownAccountException uae) {
            return CodeAndMsg.USER_NOTFINDUSERNAME.getJSON();
        } catch (LockedAccountException e) {
            return CodeAndMsg.USER_BANNED.getJSON();
        } catch (ExcessiveAttemptsException eae) {
            return CodeAndMsg.ERROR.getJSON();
        }
    }


    /**
     * 獲取token
     */
    @ResponseBody
    @PostMapping("/getToken.do")
    @ApiOperation(value = "獲取token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject getToken(String username, String password, HttpServletRequest request) {
        return CodeAndMsg.SUCESS.getJSON(JwtUtil.sign(username, password));
    }



    /**
     * 图形验证码，用assets开头可以排除shiro拦截
     */
    @RequestMapping("/assets/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        try {
            CaptchaUtil.out(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * swagger
     */
    @RequestMapping("/swagger")
    public String swagger() {
        return "swagger-ui.html";
    }






    /**
     * iframe页
     */
    @RequestMapping("/iframe")
    public String error(String url, Model model) {
        model.addAttribute("url", url);
        return "tpl/iframe.html";
    }

    /**
     * 错误页
     */
    @RequestMapping("/error")
    public String error(String code) {
        if ("403".equals(code)) {
            return "error/403.html";
        }
        return "error/404.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

    /**
     * 递归转化树形菜单
     */
    private List<Map<String, Object>> getMenuTree(List<Authorities> authorities, Integer parentId) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < authorities.size(); i++) {
            Authorities temp = authorities.get(i);
            if (temp.getIsMenu() == 0 && parentId == temp.getParentId()) {
                Map<String, Object> map = new HashMap<>();
                map.put("menuName", temp.getAuthorityName());
                map.put("menuIcon", temp.getMenuIcon());
                map.put("menuUrl", StringUtil.isBlank(temp.getMenuUrl()) ? "javascript:;" : temp.getMenuUrl());
                map.put("subMenus", getMenuTree(authorities, authorities.get(i).getAuthorityId()));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 添加登录日志
     */
    private void addLoginRecord(Integer userId, HttpServletRequest request) {
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        // 添加到登录日志
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(userId);
        loginRecord.setOsName(agentGetter.getOS());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowserType(agentGetter.getBrowser());
        loginRecord.setIpAddress(agentGetter.getIpAddr());
        loginRecordService.add(loginRecord);
    }

}

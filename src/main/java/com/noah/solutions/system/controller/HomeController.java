package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.utils.DateUtil;
import com.noah.solutions.system.service.MemberService;
import com.noah.solutions.system.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Controller()
@RequestMapping("/home")
public class HomeController {
    @Resource
    private OrderService orderService;

    @Resource
    private MemberService memberService;


    /**
     * 控制台
     */
    @RequestMapping("/console")
    public String console(Model model)throws Exception {
        Integer activeUser = memberService.countByActive(DateUtil.getFirstDayOfMonth());
        Integer allUser = memberService.countAll();
        model.addAttribute("activeUser",activeUser);
        model.addAttribute("newUser",memberService.countBytime(DateUtil.getFirstDayOfMonth()));
        model.addAttribute("allUser",allUser);
        model.addAttribute("newOrder",orderService.countBytime(DateUtil.getFirstDayOfMonth()));
        model.addAttribute("allOrder",orderService.countAll());
        model.addAttribute("newAmount",orderService.countAmountBytime(DateUtil.getFirstDayOfMonth()));
        model.addAttribute("allAmount",orderService.countAmount());
        model.addAttribute("activeUserAatio",activeUser/allUser);
        return "home/console.html";
    }

    /**
     * 消息弹窗
     */
    @RequestMapping("/message")
    public String message() {
        return "tpl/message.html";
    }

    /**
     * 修改密码弹窗
     */
    @RequestMapping("/password")
    public String password() {
        return "tpl/password.html";
    }

    /**
     * 主题设置弹窗
     */
    @RequestMapping("/theme")
    public String theme() {
        return "tpl/theme.html";
    }

    @ResponseBody
    @RequestMapping("/consoleData.do")
    public JSONObject getHomeData()throws Exception{
        return CodeAndMsg.SUCESS.getJSON().fluentPut("mouth",orderService.countByMonth(DateUtil.getCurrentYear()))
                .fluentPut("station",orderService.countGroupByPickUpStation(DateUtil.getCurrentYear()));
    }

    /**
     * 设置主题
     */
    @RequestMapping("/setTheme")
    public String setTheme(String themeName, HttpServletRequest request) {
        if (null == themeName) {
            request.getSession().removeAttribute("theme");
        } else {
            request.getSession().setAttribute("theme", themeName);
        }
        return "redirect:/";
    }
}

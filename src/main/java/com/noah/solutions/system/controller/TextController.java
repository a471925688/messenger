package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.utils.DateUtil;
import com.noah.solutions.system.service.MemberService;
import com.noah.solutions.system.service.MessageService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/text")
public class TextController {
    @Resource
    private MessageService messageService;
    @Resource
    private MemberService memberService;

    @PostMapping
    @RequestMapping("/insertMessage.do")
    public JSONObject insertMessage(Integer num)throws Exception{
        messageService.insert(num);
        return CodeAndMsg.SUCESS.getJSON();
    }

    @PostMapping
    @RequestMapping("/getHomeData.do")
    public JSONObject getHomeData(Integer num)throws Exception{
        JSONObject jsonObject = new JSONObject().fluentPut("all",memberService.countAll())
                .fluentPut("active",memberService.countByActive(DateUtil.getFirstDayOfMonth())).fluentPut("cur",memberService.countBytime(DateUtil.getFirstDayOfMonth()));
        return CodeAndMsg.SUCESS.getJSON(jsonObject);
    }

}

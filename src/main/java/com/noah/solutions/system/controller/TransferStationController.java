package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.system.entity.TransferStation;
import com.noah.solutions.system.service.TransferStationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.EDITSUCESS;

@Controller
@RequestMapping("/system/transferStation")
public class TransferStationController {

    @Resource
    private TransferStationService transferStationService;

    @RequiresPermissions("transferStation:view")
    @RequestMapping
    public String user(Model model) {
        model.addAttribute("info",transferStationService.getInfo());
        return "system/transferStation.html";
    }

    /**
     * 修改用户
     **/
    @RequiresPermissions("transferStation:edit")
    @ResponseBody
    @RequestMapping("/update")
    public JSONObject update(TransferStation transferStation)throws Exception {
        transferStationService.update(transferStation);
        return EDITSUCESS.getJSON();
    }
}

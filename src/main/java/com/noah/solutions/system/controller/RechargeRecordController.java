package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.system.entity.RechargeRecord;
import com.noah.solutions.system.service.RechargeRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/member/tokenMoney")
public class RechargeRecordController  extends BaseController {


    @Resource
    private RechargeRecordService rechargeRecordService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////

    @RequestMapping("/recharge")
    public ModelAndView recharge() {
        return new ModelAndView("member/member_top_up.html");
    }

    @RequiresPermissions("rechargeRecord:view")
    @RequestMapping()
    public ModelAndView rechargeRecord() {
        return new ModelAndView("member/rechargeRecord.html");
    }

    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询充值记录列表
     */
    @RequiresPermissions("rechargeRecord:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(Integer page, Integer limit, String startDate, String endDate, String username) {

        return SUCESS.pageData(rechargeRecordService.listPage(page, limit, startDate,endDate,username));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    @RequiresPermissions("member:addTokenRecord")
    @ResponseBody
    @RequestMapping("/addTokenRecord")
    public JSONObject addRecord(RechargeRecord rechargeRecord) {
        rechargeRecord.setOperatorId(getUserId());
        rechargeRecordService.add(rechargeRecord);
        return SUCESS.getJSON();
    }

    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

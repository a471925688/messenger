package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.system.entity.OrderReply;
import com.noah.solutions.system.service.OrderReplyService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.noah.solutions.common.exception.CodeAndMsg.ADDSUCESS;

@RestController
@RequestMapping("/order/orderReply")
public class OrderReplyController extends BaseController {


    @Resource
    private OrderReplyService orderReplyService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////


    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     *
     **/
    @RequiresPermissions("orderReply:view")
    @ResponseBody
    @RequestMapping("/getOrderReplyByOrderId")
    public JSONObject getOrderReplyByOrderId(String orderId) throws Exception{
        return ADDSUCESS.getJSON();
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     *
     **/
    @RequiresPermissions("orderReply:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(OrderReply orderReply) throws Exception{
        orderReplyService.addOrderReply(orderReply);
        return ADDSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

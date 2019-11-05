package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.service.OrderRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import java.util.List;

import static com.noah.solutions.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/order/orderRecord")
public class OrderRecordController extends BaseController {


    @Resource
    private OrderRecordService orderRecordService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("orderRecord:add")
    @RequestMapping("/locusForm")
    public ModelAndView orderLocusForm() {
        return new ModelAndView("order/order_locus_form.html");
    }


    @RequestMapping()
    public ModelAndView orderLocus(String orderId,Model model) {
        model.addAttribute("orderRecords",orderRecordService.listAllByOrderId(orderId));
        return new ModelAndView("order/order_record.html");
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加軌跡
     **/
    @RequiresPermissions("orderRecord:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(OrderRecord orderRecord,@RequestParam(value = "orderIds[]") List<String> orderIds) throws Exception{
        orderRecord.setOperatorId(getUserId()+"");
        orderRecordService.addAll(orderRecord,orderIds);
        return ADDSUCESS.getJSON();
    }


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

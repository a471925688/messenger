package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.utils.DateUtil;
import com.noah.solutions.system.entity.Order;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.noah.solutions.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/order/order")
public class OrderController extends BaseController {


    @Resource
    private OrderService orderService;


    @Resource
    private PickUpStationService pickUpStationService;

    @Resource
    private StaffService staffService;

    @Resource
    private DriverService driverService;

    @Resource
    private LabelService labelService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("order:send")
    @RequestMapping("/sendForm")
    public ModelAndView orderSend(Model model) {
        model.addAttribute("drivers",driverService.findAll());
        model.addAttribute("pickUpStations", pickUpStationService.listAll(getUser()));
        return new ModelAndView("order/order_send_form.html");
    }

    @RequiresPermissions("order:deliver")
    @RequestMapping("/deliverForm")
    public ModelAndView orderDeliver(String orderId,Model model) {
        model.addAttribute("delivers",staffService.listDispatcherAll(orderId));
        return new ModelAndView("order/order_deliver_form.html");
    }

    @RequiresPermissions("order:appointment")
    @RequestMapping("/orderAppointment")
    public ModelAndView orderAppointment() {
        return new ModelAndView("order/order_appointment_form.html");
    }


    @RequiresPermissions("orderRecord:add")
    @RequestMapping("/locusForm")
    public ModelAndView orderLocus() {
        return new ModelAndView("order/order_locus_form.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView orderRecord(Model model) {
        model.addAttribute("labels",labelService.listAll(ProjectConfig.LabelStatus.STOCK.getValue()));
        return new ModelAndView("order/order_form.html");
    }

    @RequiresPermissions("order_stock:view")
    @RequestMapping("/stock")
    public ModelAndView orderStock() {
        return new ModelAndView("order/order_stock.html");
    }


    @RequiresPermissions("order_transit:view")
    @RequestMapping("/transit")
    public ModelAndView orderTransit() {
        return new ModelAndView("order/order_transit.html");
    }


    @RequiresPermissions("order_unclaimed:view")
    @RequestMapping("/unclaimed")
    public ModelAndView orderUnclaimed() {
        return new ModelAndView("order/order_unclaimed.html");
    }

    @RequiresPermissions("order_notdispatched:view")
    @RequestMapping("/notdispatched")
    public ModelAndView orderNotdispatched() {
        return new ModelAndView("order/order_notdispatched.html");
    }

    @RequiresPermissions("order_dispatch:view")
    @RequestMapping("/dispatch")
    public ModelAndView orderDispatch() {
        return new ModelAndView("order/order_dispatch.html");
    }

    @RequiresPermissions("order_completed:view")
    @RequestMapping("/completed")
    public ModelAndView orderCompleted() {
        return new ModelAndView("order/order_completed.html");
    }

    @RequiresPermissions("order_abnormal:view")
    @RequestMapping("/abnormal")
    public ModelAndView orderAbnormal() {
        return new ModelAndView("order/order_abnormal.html");
    }
   //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 待出貨訂單列表
     */
    @RequiresPermissions("order_stock:view")
    @RequestMapping("/list_stock")
    public JSONObject listStock(Integer page, Integer limit, String startDate, String endDate, String username, String orderNo,String placeId,String labelNo)throws Exception {
        return SUCESS.pageData(orderService.listStockPage(page, limit, startDate,endDate,username,orderNo,placeId, ProjectConfig.OrderState.STOCK.getValue(),labelNo));
    }

    /**
     * 運輸中訂單列表
     */
    @RequiresPermissions("order_transit:view")
    @RequestMapping("/list_transit")
    public JSONObject listTransit(Integer page, Integer limit, String startDate, String endDate, String username,String drivername,String orderNo,String labelNo)throws Exception {
        return SUCESS.pageData(orderService.listTransitPage(page, limit, startDate,endDate,username,drivername,orderNo, ProjectConfig.OrderState.TRANSIT.getValue(),labelNo));
    }

    /**
     * 待领取(自取)訂單列表
     */
    @RequiresPermissions("order_unclaimed:view")
    @RequestMapping("/list_unclaimed")
    public JSONObject listUnclaimed(Integer page, Integer limit, String startDate, String endDate, String username,String labelNo)throws Exception {
        return SUCESS.pageData(orderService.listPage(page, limit, startDate,endDate,username, ProjectConfig.OrderState.UNCLAIMED.getValue(),labelNo));
    }

    /**
     * 待派送訂單列表
     */
    @RequiresPermissions("order_notdispatched:view")
    @RequestMapping("/list_notdispatched")
    public JSONObject listNotdispatched(Integer page, Integer limit, String startDate, String endDate, String username,String labelNo)throws Exception {
        return SUCESS.pageData(orderService.listPage(page, limit, startDate,endDate,username, ProjectConfig.OrderState.NOTDISPATCHED.getValue(),labelNo));
    }

    /**
     * 派送中訂單列表
     */
    @RequiresPermissions("order_dispatch:view")
    @RequestMapping("/list_dispatch")
    public JSONObject listDispatch(Integer page, Integer limit, String startDate, String endDate, String username,String labelNo)throws Exception {
        return SUCESS.pageData(orderService.listPage(page, limit, startDate,endDate,username, ProjectConfig.OrderState.DISPATCH.getValue(),labelNo));
    }

    /**
     * 已完成訂單列表
     */
    @RequiresPermissions("order_completed:view")
    @RequestMapping("/list_completed")
    public JSONObject listCompleted(@RequestParam("page") Integer page,@RequestParam("limit")  Integer limit,  String startDate, String endDate, String username, String orderNo,String placeId,String labelNo)throws Exception {
        return SUCESS.pageData(orderService.listStockPage(page, limit, startDate,endDate,username,orderNo,placeId, ProjectConfig.OrderState.COMPLETED.getValue(),labelNo));
    }

    /**
     * 异常訂單列表
     */
    @RequiresPermissions("order_abnormal:view")
    @RequestMapping("/list_abnormal")
    public JSONObject listAbnormal(Integer page, Integer limit, String startDate, String endDate, String username,String labelNo)throws Exception {
        return SUCESS.pageData(orderService.listPage(page, limit, startDate,endDate,username, ProjectConfig.OrderState.ABNORMAL.getValue(),labelNo));
    }

    /**
     * 获取今年订单报表数据
     */
    @RequestMapping("/getOrderReport.do")
    public JSONObject getOrderReport()throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("mouth",orderService.countByMonth(DateUtil.getCurrentYear())).fluentPut("station",orderService.countGroupByPickUpStation(DateUtil.getCurrentYear()));
        return SUCESS.getJSON(jsonObject);
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    /**
     * 入庫
     */
    @RequiresPermissions("order:addOrder")
    @RequestMapping("/add")
    public JSONObject addRecord(Order order)throws Exception {
        orderService.add(order,getUserId()+"");
        return ADDSUCESS.getJSON();
    }
    /**
     * 出庫
     */
    @RequiresPermissions("order:send")
    @RequestMapping("/send")
    public JSONObject addSend(String driverId,String pickUpStationId,@RequestParam(value = "orderIds[]") List<String> orderIds)throws Exception {
        orderService.sendAll(driverId,pickUpStationId,getUserId()+"",orderIds);
        return ADDSUCESS.getJSON();
    }

    @RequiresPermissions("order:appointment")
    @RequestMapping("/appointment")
    public JSONObject appointment(String orderId,String orderAppointment)throws Exception {
        orderService.appointment(orderId,orderAppointment);
        return HANDLESUCESS.getJSON();
    }
    /**
     * 提貨點攬收
     */
    @RequiresPermissions("order:collect")
    @RequestMapping("/collect")
    public JSONObject collect(@RequestParam("orderIds[]") List<String> orderIds)throws Exception {
        orderService.collect(orderIds,getUserId()+"");
        return HANDLESUCESS.getJSON();
    }

    /**
     * 派送
     */
    @RequiresPermissions("order:deliver")
    @RequestMapping("/deliver")
    public JSONObject deliver(@RequestParam("orderIds[]") List<String> orderIds,String dispatcherId)throws Exception {
        orderService.deliver(orderIds,dispatcherId,getUserId()+"");
        return HANDLESUCESS.getJSON();
    }

    /**
     * 領取(簽收)
     */
    @RequiresPermissions("order:receive")
    @RequestMapping("/receive")
    public JSONObject receive(String orderId,String pickUpCode)throws Exception {
        orderService.receive(orderId,pickUpCode,getUserId()+"");
        return HANDLESUCESS.getJSON();
    }

    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

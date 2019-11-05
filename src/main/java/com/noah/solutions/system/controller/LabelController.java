package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.system.entity.Label;
import com.noah.solutions.system.service.DriverService;
import com.noah.solutions.system.service.LabelService;
import com.noah.solutions.system.service.OrderService;
import com.noah.solutions.system.service.PickUpStationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import java.util.ArrayList;

import static com.noah.solutions.common.BaseController.getUser;
import static com.noah.solutions.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/label/label")
public class LabelController extends BaseController {


    @Resource
    private LabelService labelService;
    @Resource
    private DriverService driverService;
    @Resource
    private OrderService orderService;

    @Resource
    private PickUpStationService pickUpStationService;
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("label_stock:view")
    @RequestMapping("/stock")
    public ModelAndView labelStockView(Model model) {
        return new ModelAndView("label/labelStock.html");
    }
    @RequiresPermissions("label_send:view")
    @RequestMapping("/sendView")
    public ModelAndView labelSendView(Model model) {
        return new ModelAndView("label/labelSend.html");
    }
    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        return new ModelAndView("label/label_form.html");
    }

    @RequiresPermissions("label:send")
    @RequestMapping("/sendForm")
    public ModelAndView orderSend(Model model) {
        model.addAttribute("drivers",driverService.findAll());
        model.addAttribute("pickUpStations", pickUpStationService.listAll(getUser()));
        return new ModelAndView("label/label_send_form.html");
    }
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询袋子列表(未出庫)
     */
    @RequiresPermissions("label_stock:view")
    @ResponseBody
    @RequestMapping("/list_stock")
    public JSONObject listSock(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, Label label) {
        label.setStatus(ProjectConfig.LabelStatus.STOCK.getValue());
        return SUCESS.pageData(labelService.list(page, limit, label));
    }

    /**
     * 查询袋子列表(已出庫)
     */
    @RequiresPermissions("label_send:view")
    @ResponseBody
    @RequestMapping("/list_send")
    public JSONObject listSend(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, Label label) {
        label.setStatus(ProjectConfig.LabelStatus.SEND.getValue());
        return SUCESS.pageData(labelService.list(page, limit, label));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加
     **/
    @RequiresPermissions("label:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(Label label) throws Exception{
        labelService.add(label);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }

    /**
     * 编辑
     *
     **/
//    @RequiresPermissions("label:edit")
//    @ResponseBody
//    @RequestMapping("/edit")
//    public JSONObject edit(Label label) throws Exception{
//        labelService.update(label);
//        return EDITSUCESS.getJSON();
//    }

    /**
     * 出庫
     *
     **/
    @RequiresPermissions("label:send")
    @ResponseBody
    @RequestMapping("/send")
    public JSONObject send(String no,String driverId,String pickUpStationId) throws Exception{
        orderService.sendAll(driverId,pickUpStationId,getUserId()+"",new ArrayList<>(),no);
        return DELSUCESS.getJSON();
    }

    /**
     * 刪除
     *
     **/
    @RequiresPermissions("label:delete")
    @ResponseBody
    @RequestMapping("/del")
    public JSONObject del(String id) throws Exception{
        labelService.delete(id);
        return DELSUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

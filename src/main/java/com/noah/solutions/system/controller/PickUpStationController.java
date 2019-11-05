package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.system.entity.PickUpStation;
import com.noah.solutions.system.service.PickUpStationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/site/pickUpStation")
public class PickUpStationController {


    @Resource
    private PickUpStationService pickUpStationService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("pickUpStation:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        return new ModelAndView("site/pickUpStation.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        return new ModelAndView("site/pickUpStation_form.html");
    }


    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询提货点列表
     */
    @RequiresPermissions("pickUpStation:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, PickUpStation pickUpStation) {
        return SUCESS.pageData(pickUpStationService.list(page, limit, pickUpStation));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加
     **/
    @RequiresPermissions("pickUpStation:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(PickUpStation pickUpStation) throws Exception{
        pickUpStationService.add(pickUpStation);
        return CodeAndMsg.ADDSUCESS.getJSON();
    }

    /**
     * 编辑
     *
     **/
    @RequiresPermissions("pickUpStation:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(PickUpStation pickUpStation) throws Exception{
        pickUpStationService.update(pickUpStation);
        return EDITSUCESS.getJSON();
    }


    /**
     * 刪除
     *
     **/
    @RequiresPermissions("pickUpStation:delete")
    @ResponseBody
    @RequestMapping("/del")
    public JSONObject del(String id) throws Exception{
        pickUpStationService.delete(id);
        return DELSUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.service.DriverService;
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
@RequestMapping("/driver/driver")
public class DriverController {


    @Resource
    private DriverService driverService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("driver:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        return new ModelAndView("driver/driver.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        return new ModelAndView("driver/driver_form.html");
    }


    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询会员列表
     */
    @RequiresPermissions("driver:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, User user) {
        return SUCESS.pageData(driverService.list(page, limit, user));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加用户
     **/
    @RequiresPermissions("driver:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(User user) throws Exception{
        driverService.add(user);
        return ADDSUCESS.getJSON();
    }


    /**
     * 编辑用户
     **/
    @RequiresPermissions("driver:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(User user) throws Exception{
        driverService.update(user);
        return EDITSUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

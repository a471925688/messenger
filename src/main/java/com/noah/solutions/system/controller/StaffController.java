package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.system.entity.Staff;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.service.PickUpStationService;
import com.noah.solutions.system.service.StaffService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static com.noah.solutions.common.ProjectConfig.UserType.STAFF;
import static com.noah.solutions.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/site/staff")
public class StaffController extends BaseController {


    @Resource
    private StaffService staffService;

    @Resource
    private PickUpStationService pickUpStationService;


    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  start//////////////////////////////////////////////////////////
    @RequiresPermissions("staff:view")
    @RequestMapping
    public ModelAndView user(Model model) {
        return new ModelAndView("site/staff.html");
    }

    @RequestMapping("/editForm")
    public ModelAndView editForm(Model model) {
        model.addAttribute("stations",pickUpStationService.listAll(getUser()));
        return new ModelAndView("site/staff_form.html");
    }


    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////
    //////////////////////////////页面加载部分  end//////////////////////////////////////////////////////////





    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询員工列表
     */
    @RequiresPermissions("staff:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, User user) {
        Staff staff = new Staff();
        staff.setUser(user);
        return SUCESS.pageData(staffService.list(page, limit, staff,getUserId()));
    }

    /**
     * 查询派送員列表
     */
    @RequiresPermissions("order:deliver")
    @ResponseBody
    @RequestMapping("/listDispatcher")
    public JSONObject listDispatcher(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit,String nickName,String orderId) {
        return SUCESS.pageData(staffService.listDispatcher(page, limit,nickName,orderId));
    }
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////

    /**
     * 添加用户
     **/
    @RequiresPermissions("staff:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(Staff staff) throws Exception{
        staffService.add(staff);
        return ADDSUCESS.getJSON();
    }


    /**
     * 编辑用户
     **/
    @RequiresPermissions("staff:edit")
    @ResponseBody
    @RequestMapping("/edit")
    public JSONObject edit(Staff staff) throws Exception{
        staffService.update(staff);
        return EDITSUCESS.getJSON();
    }
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

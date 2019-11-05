package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.system.entity.BannerType;
import com.noah.solutions.system.service.BannerTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;

/**
 * 類型管理
 */
@Controller
@RequestMapping("/banner/bannerType")
public class BannerTypeController extends BaseController {
    @Autowired
    private BannerTypeService bannerTypeService;

    @RequiresPermissions("bannerType:view")
    @RequestMapping
    public String bannerType(Model model) {
        return "banner/banner_type.html";
    }

    @RequestMapping("/editForm")
    public String addBannerType(Model model) {
        return "banner/banner_type_form.html";
    }

    /**
     * 查询類型列表
     */
    @RequiresPermissions("bannerType:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, BannerType bannerType) {
        return SUCESS.pageData(bannerTypeService.list(page, limit, bannerType));
    }

    /**
     * 添加類型
     **/
    @RequiresPermissions("bannerType:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(BannerType bannerType) throws Exception{
        bannerTypeService.add(bannerType);
        return SUCESS.getJSON();
    }

    /**
     * 修改類型
     **/
    @RequiresPermissions("bannerType:edit")
    @ResponseBody
    @RequestMapping("/update")
    public JSONObject update(BannerType bannerType)throws Exception {
        bannerTypeService.update(bannerType);
        return SUCESS.getJSON();
    }


}

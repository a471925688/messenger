package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.system.entity.Banner;
import com.noah.solutions.system.service.BannerService;
import com.noah.solutions.system.service.BannerTypeService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.*;

/**
 * 廣告管理
 */
@Controller
@RequestMapping("/banner/banner")
public class BannerController extends BaseController {
    @Autowired
    private BannerService bannerService;

    @Resource
    private BannerTypeService bannerTypeService;

    @RequiresPermissions("banner:view")
    @RequestMapping
    public String banner(Model model) {
        model.addAttribute("bannerTypes",bannerTypeService.listAll());
        return "banner/banner.html";
    }

    @RequestMapping("/editForm")
    public String addBanner(Model model) {
        model.addAttribute("bannerTypes",bannerTypeService.listAll());
        return "banner/banner_form.html";
    }

    /**
     * 查询廣告列表
     */
    @RequiresPermissions("banner:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, Banner banner) {
        return SUCESS.pageData(bannerService.list(page, limit, banner));
    }

    /**
     * 添加廣告
     **/
    @RequiresPermissions("banner:add")
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(Banner banner) throws Exception{
        bannerService.add(banner);
        return ADDSUCESS.getJSON();
    }

    /**
     * 修改廣告
     **/
    @RequiresPermissions("banner:edit")
    @ResponseBody
    @RequestMapping("/update")
    public JSONObject update(Banner banner)throws Exception {
        bannerService.update(banner);
        return SUCESS.getJSON();
    }

    /**
     * 修改廣告
     **/
    @RequiresPermissions("banner:delete")
    @ResponseBody
    @RequestMapping("/del")
    public JSONObject del(String id)throws Exception {
        bannerService.del(id);
        return DELSUCESS.getJSON();
    }



}

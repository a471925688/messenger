package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.system.service.DriverService;
import com.noah.solutions.system.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;


@RestController
@RequestMapping("/app/driver")
@Api(value = "司機專屬", tags = "driver")
public class AppDriverController {

    @Resource
    private DriverService driverService;

    /**
     * 获取所有司機
     * @return
     * @throws Exception
     */
    @RequiresPermissions("driver:view")
    @PostMapping("/listAll.do")
    @ApiOperation(value = "获取所有司機")
    public JSONObject listAll()throws Exception{
        return SUCESS.getJSON(driverService.findAll());
    }

}

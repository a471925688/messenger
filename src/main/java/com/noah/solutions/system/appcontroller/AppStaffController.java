package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.system.service.DriverService;
import com.noah.solutions.system.service.StaffService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;


@RestController
@RequestMapping("/app/site")
@Api(value = "提貨點工作人員", tags = "site")
public class AppStaffController extends BaseController {

    @Resource
    private StaffService staffService;

    /**
     * 查询派送員列表
     */
    @RequiresPermissions("order:deliver")
    @ApiOperation(value = "提货点查询騎手列表")
    @PostMapping("/listDispatcher")
    public JSONObject listDispatcher() {
        return SUCESS.getJSON(staffService.listDispatcher(getUser()));
    }
}

package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.system.repository.DaoUtil.Page;
import com.noah.solutions.system.service.PickUpStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/app/pickUpStation")
@Api(value = "提货点api", tags = "pickUpStation")
public class AppPickUpStationController extends BaseController {


    @Resource
    private PickUpStationService pickUpStationService;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    /**
     * 查询提货点列表
     */
    @ApiOperation(value = "查询所有提货点信息")
    @PostMapping("/listAll.do")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject listAll(Integer page,Integer limit) {
        return SUCESS.getJSON(pickUpStationService.listPage(getUser(), PageRequest.of(page-1,limit)));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////











    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  start//////////////////////////////////////////////////////////


    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////
    //////////////////////////////其他部分  end//////////////////////////////////////////////////////////


}

package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.system.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;


@RestController
@RequestMapping("/app/place")
@Api(value = "地区api", tags = "place")
public class AppPlaceController {

    @Resource
    private PlaceService placeService;

    /**
     * 获取所有地区信息(树形)
     * @return
     * @throws Exception
     */
    @PostMapping("/ztreeList.do")
    @ApiOperation(value = "获取地区信息")
    public JSONObject ztreeList()throws Exception{
        return SUCESS.getJSON(placeService.ztreeAll());
    }

}

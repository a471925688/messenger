package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.system.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/app/banner")
@Api(value = "廣告api", tags = "banner")
public class AppBannerController {
    @Resource
    private BannerService bannerService;

    /**
     * 獲取首頁輪播廣告
     * @return
     * @throws Exception
     */
    @PostMapping("/imageBanner.do")
    @ApiOperation(value = "獲取首頁廣告(圖片,大屏幕輪播)")
    public JSONObject imageBanner()throws Exception{
        return SUCESS.getJSON( bannerService.getImageBanner());
    }


    /**
     * 獲取首頁廣告
     * @return
     * @throws Exception
     */
    @PostMapping("/infoBanner.do")
    @ApiOperation(value = "獲取首頁廣告(首頁中間文字信息)")
    public JSONObject infoBanner()throws Exception{
        return SUCESS.getJSON( bannerService.getInfoBanner());
    }

}

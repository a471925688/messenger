package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.system.service.LabelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/app/label")
@Api(value = "標籤(袋子)相關api", tags = "label")
public class AppLableController {
    @Resource
    private LabelService labelService;

    /**
     * 獲取所有未出庫標籤
     * @return
     * @throws Exception
     */
    @PostMapping("/getAllStockLabel.do")
    @ApiOperation(value = "獲取所有未出庫標籤（袋子）")
    public JSONObject getAllStockLabel()throws Exception{
        return SUCESS.getJSON( labelService.listAll(ProjectConfig.LabelStatus.STOCK.getValue()));
    }

//    /**
//     * 獲取新標籤（包裹號）
//     * @return
//     * @throws Exception
//     */
//    @PostMapping("/getNewLabel.do")
//    @ApiOperation(value = "獲取新標籤（袋子）")
//    public JSONObject getNewLabel()throws Exception{
//        return SUCESS.getJSON(labelService.getNewNo());
//    }
}

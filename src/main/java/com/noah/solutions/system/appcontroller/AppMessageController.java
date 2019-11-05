package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.system.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;

@RestController
@RequestMapping("/app/message")
@Api(value = "消息api", tags = "message")
public class AppMessageController {
    @Resource
    private MessageService messageService;

    /**
     * 獲取當前用戶消息
     * @param isRead
     * @return
     * @throws Exception
     */
    @PostMapping("/getMessage.do")
    @ApiOperation(value = "獲取當前用戶消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isRead", value = "消息類型(true.已讀,false.未讀,null.全部)",  dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query")
    })
    public JSONObject getMessage(Boolean isRead,Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON( messageService.list(isRead, PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC,"messageId"))));
    }

    /**
     * 標記已讀
     * @param messageId
     * @return
     * @throws Exception
     */
    @PostMapping("/readMessage.do")
    @ApiOperation(value = "標記已讀")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "messageId", value = "消息id", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject readMessage(String messageId)throws Exception{
        messageService.read(messageId);
        return SUCESS.getJSON( );
    }

}

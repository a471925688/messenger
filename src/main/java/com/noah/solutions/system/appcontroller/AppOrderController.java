package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.system.entity.Order;
import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.repository.DaoUtil.Page;
import com.noah.solutions.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.noah.solutions.common.exception.CodeAndMsg.*;


@RestController
@RequestMapping("/app/order")
@Api(value = "訂單相關api", tags = "order")
public class AppOrderController extends BaseController {

    @Resource
    private OrderService orderService;

    @Resource
    private OrderRecordService orderRecordService;

    @Resource
    private OrderReplyService orderReplyService;

    /**
     * 入庫
     */
    @RequiresPermissions("order:addOrder")
    @PostMapping("/addOrder")
    @ApiOperation(value = "訂單入庫", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "labelNo", value = "標籤號（包裹號）", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderNo", value = "快遞單號", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recId", value = "地址id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "goodsType", value = "類型", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "goodsWeight", value = "重量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "goodsVolume", value = "體積", required = true, dataType = "Float", paramType = "query"),
            @ApiImplicitParam(name = "orderTokenMoney", value = "運費", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject addOrder(@RequestParam("labelNo") String labelNo,
                               @RequestParam("orderNo") String orderNo,
                               @RequestParam("recId") String recId,
                               @RequestParam("goodsType") String goodsType,
                               @RequestParam("goodsWeight")Integer goodsWeight,
                               @RequestParam("goodsVolume")Float goodsVolume,
                               @RequestParam("orderTokenMoney")String orderTokenMoney)throws Exception {
        Order order = new Order(labelNo,orderNo,recId,goodsType,goodsWeight,goodsVolume,orderTokenMoney);
        orderService.add(order,getUserId()+"");
        return ADDSUCESS.getJSON();
    }
    /**
     * 出庫
     */
    @RequiresPermissions("order:send")
    @PostMapping("/sendOrder")
    @ApiOperation(value = "訂單出庫(批量出庫,標籤出庫)", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "driverId", value = "司機id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pickUpStationId", value = "提貨點id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderIds", value = "訂單id(labelNo和orderIds至少必填一個)",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labelNo", value = "標籤號(labelNo和orderIds至少必填一個)",  dataType = "String", paramType = "query")
    })
    public JSONObject sendOrder(@RequestParam("driverId") String driverId,
                                @RequestParam("pickUpStationId")String pickUpStationId,
                               String  orderIds,String  labelNo)throws Exception {
        orderService.sendAll(driverId,pickUpStationId,getUserId()+"",idsHandle(orderIds),labelNo);
        return HANDLESUCESS.getJSON();
    }

    /**
     * 提貨點攬收
     */
    @RequiresPermissions("order:collect")
    @PostMapping("/collectOrder")
    @ApiOperation(value = "提貨點攬收(批量攬件,標籤攬件)", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderIds", value = "訂單id(labelNo和orderIds至少必填一個)", paramType = "query"),
            @ApiImplicitParam(name = "labelNo", value = "標籤號(labelNo和orderIds至少必填一個)",  dataType = "String", paramType = "query")
    })
    public JSONObject collectOrder(String  orderIds,String  labelNo)throws Exception {
        orderService.collect( idsHandle(orderIds),getUserId()+"",labelNo);
        return HANDLESUCESS.getJSON();
    }


    /**
     * 添加軌跡
     **/
    @RequiresPermissions("orderRecord:add")
    @PostMapping("/addRecord")
    @ApiOperation(value = "添加訂單軌跡(批量添加,標籤添加)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderIds", value = "訂單id(labelNo和orderIds至少必填一個)",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "labelNo", value = "標籤號(labelNo和orderIds至少必填一個)",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "title", value = "標題(如:關卡被扣)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "place", value = "地址(如:澳門關卡)", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tbExplain", value = "描述", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "描述(true:正常,false:異常)", required = true, dataType = "Boolean", paramType = "query")
    })
    public JSONObject addRecord(String orderIds,String  labelNo,
                                @RequestParam("title") String title,
                                @RequestParam("place") String place,
                                @RequestParam("tbExplain")String tbExplain,
                                @RequestParam("status")Boolean status) throws Exception{
        OrderRecord orderRecord = new OrderRecord(title,place,tbExplain,getUserId()+"",status);
        orderRecordService.addAll(orderRecord,idsHandle(orderIds),labelNo);
        return ADDSUCESS.getJSON();
    }
    /**
     * 領取(簽收)
     */
    @RequiresPermissions("order:receive")
    @ApiOperation(value = "訂單提貨(簽收)")
    @PostMapping("/receive")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "訂單id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pickUpCode", value = "提貨碼)", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject receive(@RequestParam("orderId") String orderId,@RequestParam("pickUpCode") String pickUpCode)throws Exception {
        orderService.receive(orderId,pickUpCode,getUserId()+"");
        return HANDLESUCESS.getJSON();
    }

    /**
     * 派送
     */
    @RequiresPermissions("order:deliver")
    @ApiOperation(value = "分配訂單(派送)")
    @PostMapping("/deliver")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderIds", value = "訂單id(labelNo和orderIds至少必填一個)",dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "騎手id", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject deliver(@RequestParam("orderIds") String orderIds,String userId)throws Exception {
        orderService.deliver(idsHandle(orderIds),userId,getUserId()+"");
        return HANDLESUCESS.getJSON();
    }
    /**
     * 訂單留言
     * @return
     * @throws Exception
     */
    @PostMapping("/addOrderReply.do")
    @ApiOperation(value = "訂單留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "訂單id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "內容", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject addOrderReply(String content,String orderId)throws Exception{
        orderReplyService.addOrderReply(content,orderId);
        return HANDLESUCESS.getJSON();
    }


    /**
     * 標記已讀
     * @param orderReplyId
     * @return
     * @throws Exception
     */
    @PostMapping("/readOrderReply.do")
    @ApiOperation(value = "留言標記已讀")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderReplyId", value = "留言id", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject readOrderReply(String orderReplyId)throws Exception{
        orderReplyService.readOrderReply(orderReplyId);
        return SUCESS.getJSON( );
    }


    /**
     * 查看待出庫訂單
     * @return
     * @throws Exception
     */
    @PostMapping("/stockList.do")
    @ApiOperation(value = "獲取待出庫訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject stockList(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.stockList(getUser(), PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }

    /**
     * 獲取運輸中（未攬件）訂單
     * @return
     * @throws Exception
     */
    @PostMapping("/transitList.do")
    @ApiOperation(value = "獲取運輸中（未攬件）訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })

    public JSONObject transitList(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.transitList(getUser(), PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }

    /**
     * 獲取所有訂單
     * @return
     * @throws Exception
     */
    @PostMapping("/listPage.do")
    @ApiOperation(value = "獲取所有訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject listPage(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.listPage(PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id")),getUser()));
    }


    /**
     * 獲取已攬件（待派送,待領取）訂單
     * @return
     * @throws Exception
     */
    @PostMapping("/packagedList.do")
    @ApiOperation(value = "獲取已攬件（待派送,待領取）訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject packagedList(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.packagedList(getUser(), PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }

    /**
     * 獲取待派送訂單
     * @return
     * @throws Exception
     */
    @PostMapping("/notdispatchedList.do")
    @ApiOperation(value = "獲取待派送訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject notdispatchedList(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.listByState(getUser(),ProjectConfig.OrderState.NOTDISPATCHED.getValue(), PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }

    /**
     * 獲取待取貨訂單
     * @return
     * @throws Exception
     */
    @PostMapping("/unclaimedList.do")
    @ApiOperation(value = "獲取待取貨訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject unclaimedList(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.listByState(getUser(),ProjectConfig.OrderState.UNCLAIMED.getValue(), PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }



    /**
     * 獲取庫中（派送之前）訂單列表詳情
     * @return
     * @throws Exception
     */
    @PostMapping("/transportList.do")
    @ApiOperation(value = "獲取庫中（派送之前）訂單列表詳情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject transportList(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.transportList(getUser(), PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }


    /**
     * 獲取派送中訂單列表詳情
     * @return
     * @throws Exception
     */
    @PostMapping("/deliverList.do")
    @ApiOperation(value = "獲取派送中訂單列表詳情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject deliverList(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.deliverList(getUser(), PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }

    /**
     * 獲取已完成訂單列表詳情
     * @return
     * @throws Exception
     */
    @PostMapping("/completedList.do")
    @ApiOperation(value = "獲取已完成訂單詳情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject completedList(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderService.completedList(getUser(), PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }


    /**
     * 查询订单物流信息
     * @return
     * @throws Exception
     */
    @PostMapping("/orderRecord.do")
    @ApiOperation(value = "查询订单物流信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "條數", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject orderRecord(String orderId,Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderRecordService.listPageByOrderId(orderId,PageRequest.of(page-1,limit, new Sort(Sort.Direction.DESC,"id"))));
    }

    /**
     * 查詢單個訂單詳情
     * @return
     * @throws Exception
     */
    @PostMapping("/orderById.do")
    @ApiOperation(value = "查詢單個訂單詳情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject orderById(String orderId)throws Exception{
        return SUCESS.getJSON(orderService.getUserOrderView(orderId,getUser()));
    }

    /**
     * 查詢訂單
     * @return
     * @throws Exception
     */
    @PostMapping("/orderByRecOrNo.do")
    @ApiOperation(value = "查詢單個訂單詳情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recId", value = "地址id", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "orderNo", value = "快递单号", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pickUpCode", value = "提货码", required = false, dataType = "String", paramType = "query")
    })
    public JSONObject orderByRecIdOrNo(@RequestParam(value = "recId",required = false) String recId,
                                       @RequestParam(value = "orderNo",required = false) String orderNo,
                                       @RequestParam(value = "pickUpCode",required = false)String pickUpCode)throws Exception{
        return SUCESS.getJSON(orderService.getOrderViewByUser(recId,orderNo,pickUpCode,getUser()));
    }


    /**
     * 獲取當前用戶未完成訂單最新物流
     * @return
     * @throws Exception
     */
    @PostMapping("/orderNewRecord.do")
    @ApiOperation(value = "用戶所有未完成訂單最新物流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject orderNewRecord(Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderRecordService.listAllNewRecord(PageRequest.of(page-1,limit)));
    }


    /**
     * 獲取訂單留言
     * @return
     * @throws Exception
     */
    @PostMapping("/orderReply.do")
    @ApiOperation(value = "獲取訂單留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isRead", value = "消息類型(true.已讀,false.未讀,null.全部)",  dataType = "Boolean", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject orderReply(Boolean isRead,String orderId,Integer page,Integer limit)throws Exception{
        return SUCESS.getJSON(orderRecordService.orderReply(orderId,isRead,PageRequest.of(page-1,limit)));
    }


    private List<String> idsHandle(String ids){
        if(StringUtils.isEmpty(ids))
            return new ArrayList<>();
        ids = ids.replaceAll("[\\[\\]]","");
        String[] o = ids.trim().split(",");
        List<String> resIds = new ArrayList<>();
        for (String orderId:o) {
            resIds.add(Integer.valueOf(orderId.trim())+"");
        }
        return resIds;
    }

}

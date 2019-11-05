package com.noah.solutions.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.shiro.ShiroUtil;
import com.noah.solutions.common.utils.StringUtil;
import com.noah.solutions.system.entity.*;
import com.noah.solutions.system.repository.*;
import com.noah.solutions.system.repository.DaoUtil.Criteria;
import com.noah.solutions.system.repository.DaoUtil.Criterion;
import com.noah.solutions.system.repository.DaoUtil.Restrictions;
import com.noah.solutions.system.repository.DaoUtil.SimpleExpression;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.common.utils.DateUtil;
import com.noah.solutions.common.utils.UUIDUtil;
import com.noah.solutions.system.service.OrderService;
import com.noah.solutions.system.thirdPartyInterface.JiGuangMessage;
import com.noah.solutions.system.view.UserOrderView;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.noah.solutions.common.ProjectConfig.MessageType.ORDER;
import static com.noah.solutions.common.ProjectConfig.OrderState.*;
import static com.noah.solutions.common.ProjectConfig.ReceAddresMode.SELFMENTION;
import static com.noah.solutions.common.ProjectConfig.UserType.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderRecordRepository orderRecordRepository;

    @Resource
    private PickUpStationRepository pickUpStationRepository;

    @Resource
    private ReceAddresRepository receAddresRepository;

    @Resource
    private UserRepository userRepository;
    @Resource
    private StaffRepository staffRepository;

    @Resource
    private MessageRepository messageRepository;

    @Resource
    private LabelRepository labelRepository;

    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page listPage(Integer page, Integer limit, Order order) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return orderRepository.findAll(Example.of(order,matcher), PageRequest.of(page-1,limit));
    }



    @Override
    public Page listPage(Integer page, Integer limit, String startDate, String endDate, String username,Integer orderState,String labelNo)throws Exception{
        return listPage(page,limit,startDate,endDate,username,null,null,null,orderState,labelNo);
    }
    @Override
    public Page listStockPage(Integer page, Integer limit, String startDate, String endDate, String username,String orderNo,String placeId, Integer orderState,String labelNo)throws Exception{
       return listPage(page,limit,startDate,endDate,username,orderNo,null,placeId,orderState,labelNo);
    }

    @Override
    public Page listTransitPage(Integer page, Integer limit, String startDate, String endDate, String username,String drivername, String orderNo,Integer orderState,String labelNo)throws Exception{
        return listPage(page,limit,startDate,endDate,username,orderNo,drivername,null,orderState,labelNo);
    }


    @Override
    public List<UserOrderView> transportList(User user,Pageable pageable) {
        List<Integer> state = new ArrayList<>();
        state.add(STOCK.getValue());
        state.add(TRANSIT.getValue());
        state.add(UNCLAIMED.getValue());
        state.add(NOTDISPATCHED.getValue());
        return listByUser(user,state,pageable);
    }

    @Override
    public List<UserOrderView> deliverList(User user,Pageable pageable) {
        List<Integer> state = new ArrayList<>();
        state.add(DISPATCH.getValue());
        return listByUser(user,state,pageable);
    }

    @Override
    public List<UserOrderView> completedList(User user,Pageable pageable) {
        List<Integer> state = new ArrayList<>();
        state.add(COMPLETED.getValue());
        return listByUser(user,state,pageable);
    }
    @Override
    public List countByMonth(String year) throws Exception {
        List datas = new ArrayList();
        JSONArray curData =JSONObject.parseArray(JSONObject.toJSONString(orderRepository.groupByMonth(year))) ;
        Integer min = curData.getJSONArray(0).getInteger(0);
        Integer max = curData.getJSONArray(curData.size()-1).getInteger(0);
        for (int i = 1;i<13;i++){
            Integer curCount = 0;
            if(i>max){
                break;
            }
            if(min<=i){
                curCount = curData.getJSONArray(i-min).getInteger(1);
            }
            datas.add(curCount);
        }
        return datas;
    }
    @Override
    public List countGroupByPickUpStation(String year) throws Exception {
        return orderRepository.groupByPickUpStation(year);
    }





    @Override
    public List<UserOrderView>  listPage(Pageable pageable,User user) {
        return listByUser(user,null,pageable);
    }
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////

    @Transactional
    @Override
    public void add(Order order,String userId)throws Exception {
        ReceAddres receAddres = receAddresRepository.findByRecId(order.getRecId());
        if(org.springframework.util.StringUtils.isEmpty(order.getLabelNo()))
            throw new CustormException("標籤號不能為空",1);
        if(receAddres==null)
            throw new CustormException("收货地址id不存在",1);
        if(orderRepository.existsByOrderNo(order.getOrderNo()))
            throw new CustormException("快遞單號已存在,請認真核對",1);
        order.setUserId(receAddres.getUserId());
        order.setPickUpCode(UUIDUtil.randomUUID8());
        order.setPickUpStationIdNext(receAddres.getPickUpStationId());
        order = orderRepository.saveAndFlush(order);
        saveOrderRecord(new OrderRecord("驗收完成","珠海中轉站","包裹完整無損",userId,true,order.getOrderId()));
        saveOrderRecord(new OrderRecord("稱重完成","珠海中轉站","包裹重量："+order.getGoodsWeight()+"kg,體積："+order.getGoodsVolume()+"m³,需要支付："+order.getOrderTokenMoney()+"代幣",userId,true,order.getOrderId()));
        saveOrderRecord(new OrderRecord("已入庫","珠海中轉站","包裹已入庫",userId,true,order.getOrderId()));
        Message message = new Message(ORDER.getValue(),order.getUserId(),"入庫通知","包裹已到達珠海中轉站並稱重入庫,需支付 "+order.getOrderTokenMoney()+" 代幣");
        messageRepository.save(message);
        jiguang(message, Integer.valueOf(receAddres.getUserId()));
    }

    @Transactional
    @Override
    public void update(Order order) {
        Order order1 = orderRepository.getOne(order.getPickUpStationId());
        BeanUtils.copyNotNullProperties(order,order1);
        order1.setUpdateTime(new Date());
        orderRepository.save(order1);
    }


    @Transactional
    @Override
    public void delete(String id) {
        orderRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void sendAll(String driverId, String pickUpStationId,String userId,List<String> orderIds) {
        orderIds.forEach(v->{
            orderRepository.sendOrder(pickUpStationId,driverId,v, STOCK.getValue());
            saveOrderRecord(new OrderRecord("包裹出庫","珠海中轉站","包裹已出庫,準備發往:"+pickUpStationRepository.findNameById(pickUpStationId),userId,true,v));
        });

    }

    @Override
    @Transactional
    public void sendAll(String driverId, String pickUpStationId,String userId,List<String> orderIds,String labelNo) {
        if(!StringUtils.isEmpty(labelNo)){
            orderIds.addAll(orderRepository.findAllIdsByLabelNoAndState(labelNo, STOCK.getValue()));
            labelRepository.updateStatus(labelNo,ProjectConfig.LabelStatus.SEND.getValue());
        }
        sendAll(driverId,pickUpStationId,userId,orderIds);
    }


    @Override
    public UserOrderView getUserOrderView(String orderId, User user) {
        UserOrderView userOrderView = null;
        if(user.getType().equals( MEMBER.getValue())){
            userOrderView =  orderRepository.findUserOrderViewByOrderIdAndUserId(orderId,user.getUserId()+"");
        }else {
            userOrderView =  orderRepository.findUserOrderViewByOrderId(orderId);
        }
        userOrderView.setOrderRecords(orderRecordRepository.findAllByOrderIdOrderByIdDesc(orderId));
        return userOrderView;
    }


    @Override
    public UserOrderView getOrderViewByUser(String recId, String orderNo,String pickUpCode, User user) {
        Order order = new Order();
        order.setRecId(recId);
        order.setOrderNo(orderNo);
        order.setPickUpCode(pickUpCode);
        ExampleMatcher matcher = ExampleMatcher.matching();
        List<Order> orders = orderRepository.findAll(Example.of(order,matcher),Sort.by(Sort.Direction.DESC,"orderId"));
        if(orders.size()>0){
            order =  orders.get(0);
            if(!StringUtils.isEmpty(order.getOrderId())){
                UserOrderView userOrderView = new UserOrderView();
                BeanUtils.copyNotNullProperties(order,userOrderView);
                userOrderView.setOrderRecords(orderRecordRepository.findAllByOrderIdOrderByIdDesc(order.getOrderId()));
                return userOrderView;
            }
        }
        return null;
    }

    @Override
    public Integer countBytime(Date firstDayOfMonth) {
        return orderRepository.countAllByCreateTimeGreaterThan(firstDayOfMonth);
    }

    @Override
    public Long countAll() {
        return orderRepository.count();
    }

    @Override
    public Long countAmountBytime(Date firstDayOfMonth) {
        Long val = orderRepository.countAmountBytime(firstDayOfMonth);
        if(val==null){
            val=0L;
        }
        return val;
    }

    @Override
    public Long countAmount() {
        return orderRepository.countAmount();
    }


    @Override
    public List<UserOrderView> stockList(User user, PageRequest pageable) {
        List<Integer> state = new ArrayList<>();
        state.add(STOCK.getValue());
        return listByUser(user,state,pageable);
    }

    @Override
    public List<UserOrderView> transitList(User user, PageRequest pageable) {
        List<Integer> state = new ArrayList<>();
        state.add(TRANSIT.getValue());
        return listByUser(user,state,pageable);
    }

    @Override
    public List<UserOrderView> listByState(User user,Integer state, PageRequest of){
        List<Integer> states = new ArrayList<>();
        states.add(state);
        return listByUser(user,states,of);
    }


    @Override
    public List<UserOrderView> packagedList(User user, PageRequest pageable) {
        List<Integer> state = new ArrayList<>();
        state.add(UNCLAIMED.getValue());
        state.add(NOTDISPATCHED.getValue());
        return listByUser(user,state,pageable);
    }
    @Override
    @Transactional
    public void appointment(String orderId, String orderAppointment) {
        orderRepository.updateOrderAppointment(orderId,DateUtil.parseDate(orderAppointment));
    }

    @Override
    @Transactional
    public void collect(List<String> orderIds,String userId)throws Exception {
        for (String orderId:orderIds) {
            collect(orderId+"",userId);
        }

    }

    @Override
    @Transactional
    public void collect(List<String> orderIds, String userId, String labelNo) throws Exception {
        if(!StringUtils.isEmpty(labelNo))
            orderIds.addAll(orderRepository.findAllIdsByLabelNoAndState(labelNo,TRANSIT.getValue()));
        collect(orderIds,userId);
    }

    @Override
    @Transactional
    public void collect(String orderId,String userId)throws Exception {
        Integer recMode = orderRepository.selectRecModeByOrderIdAndOrderState(orderId,TRANSIT.getValue());//查出收貨方式
        PickUpStation destination = orderRepository.selectDestinationByOrderIdAndOrderState(orderId);//查出目的地
        if(recMode==null)
            throw  new CustormException("訂單號:"+orderId+" 未知異常",1);
        if(recMode.equals(SELFMENTION.getValue())){
            orderRepository.updateOrderState(orderId,UNCLAIMED.getValue());
            saveOrderRecord(new OrderRecord("到達提貨點",destination.getPickUpStationName(),"包裹已到達:"+destination.getPickUpStationName()+",等待領取",userId,true,orderId));
            messageRepository.save(new Message(ORDER.getValue(),orderRepository.selectUserIdByOrderId(orderId),"等待領取","包裹已到達指定提貨點,請盡快前往領取 "));

        }else {
            orderRepository.updateOrderState(orderId,NOTDISPATCHED.getValue());
            saveOrderRecord(new OrderRecord("到達提貨點",destination.getPickUpStationName(),"包裹已到達:"+destination.getPickUpStationName()+",等待派送",userId,true,orderId));
            messageRepository.save(new Message(ORDER.getValue(),orderRepository.selectUserIdByOrderId(orderId),"準備派送","包裹已到達提貨點,等待安排派送 "));
        }
    }

    @Override
    @Transactional
    public void deliver(List<String> orderIds, String dispatcherId,String userId) {
        orderRepository.deliver(orderIds,dispatcherId);
        orderIds.forEach(v->{
            OrderRecord orderRecord = new OrderRecord("正在派送",orderRepository.selectDestinationByOrderIdAndOrderState(v).getPickUpStationName(),"包裹正在派送中,請保持電話暢通",userId,true,v);
            orderRecord.setCourierId(dispatcherId);
            orderRecordRepository.save(orderRecord);
            User user = userRepository.getOne(Integer.valueOf(dispatcherId));
            Message message = new Message(ORDER.getValue(),orderRepository.selectUserIdByOrderId(v),"派送中","您的包裹正在派送. 派送員: "+user.getNickName()+" ,聯繫電話: "+user.getPhone());
            messageRepository.save(message);
            jiguang(message, Integer.valueOf(orderRepository.getUserId(v)));
        });
    }

    @Override
    @Transactional
    public void receive(String orderId, String pickUpCode, String userId) {
        Order order = orderRepository.getOne(orderId);
        if(!order.getPickUpCode().equals(pickUpCode))
            throw new CustormException("提貨碼錯誤",1);
        String tokenMoney = userRepository.getTokenMoneyById(Integer.valueOf(order.getUserId()));
        if(Double.valueOf(order.getOrderTokenMoney())>Double.valueOf(tokenMoney)){
            Double subMoney = Double.valueOf(order.getOrderTokenMoney())-Double.valueOf(tokenMoney);
            throw new CustormException("代幣不足,請充值,需充值數量："+subMoney.intValue(),1);
        }
        userRepository.subTokenMoney(Double.valueOf(order.getOrderTokenMoney()),Integer.valueOf(order.getUserId()));
        orderRepository.updateOrderState(orderId, COMPLETED.getValue());
        orderRecordRepository.save(new OrderRecord("包裹領取",orderRepository.selectDestinationByOrderIdAndOrderState(orderId).getPickUpStationName(),"包裹已領取,領取方式:提貨碼",userId,true,orderId));
        Message message = new Message(ORDER.getValue(),orderRepository.selectUserIdByOrderId(orderId),"已簽收","您的包裹已簽收! 簽收方式: 提貨碼");
        messageRepository.save(message);
        jiguang(message, Integer.valueOf(order.getUserId()));
    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    private void saveOrderRecord(OrderRecord orderRecord){
        orderRecordRepository.save(orderRecord);
    }


    private List<UserOrderView> listByUser(User user,List<Integer> state,Pageable pageable){
        List<UserOrderView> orders = new ArrayList<>();
        if(user.getType().equals( ADMIN.getValue())){
            if(state==null){
                orders = orderRepository.findAllOrderView(pageable);
            }else {
                orders = orderRepository.findAllOrderViewByStateIn(state,pageable);
            }

        }else if(user.getType().equals(MEMBER.getValue())){
            if(state==null){
                orders = orderRepository.findAllOrderViewByUserId(user.getUserId()+"",pageable);
            }else {
                orders = orderRepository.findAllOrderViewByUserIdAndOrderStateIn(user.getUserId()+"",state,pageable);
            }
        }else if(user.getType().equals(STAFF.getValue())){
            Staff staff = staffRepository.findByUser_UserId(user.getUserId());
            if(staff.getStaffType().equals(ProjectConfig.StaffType.COURIER.getValue())){
                if(state==null){
                    orders = orderRepository.findAllOrderViewByDispatcherId(user.getUserId()+"",pageable);
                }else {
                    orders = orderRepository.findAllOrderViewByDispatcherIdAndOrderStateIn(user.getUserId()+"",state,pageable);
                }
            }else {
                String pickUpStationId = staff.getPickUpStationId();
                if(state==null){
                    orders = orderRepository.findAllOrderViewByPickUpStationId(pickUpStationId+"",pageable);
                }else {
                    orders = orderRepository.findAllOrderViewByPickUpStationIdAndOrderStateIn(pickUpStationId+"",state,pageable);
                }
            }

        }else if(user.getType().equals(DRIVER.getValue())){
            if(state==null){
                orders = orderRepository.findAllOrderViewByDriverId(user.getUserId()+"",pageable);
            }else {
                orders = orderRepository.findAllOrderViewByDriverIdAndOrderStateIn(user.getUserId()+"",state,pageable);
            }
        }
        orders.forEach(v->v.setOrderRecords(orderRecordRepository.findAllByOrderIdOrderByIdDesc(v.getOrderId())));
        return orders;
    }



    private Page listPage(Integer page, Integer limit, String startDate, String endDate, String username,String orderNo,String driver,String placeId, Integer orderState,String labelNo)throws Exception{
        Criteria criteria = new Criteria();
        criteria.add(Restrictions.eq("orderState",orderState,true));
        if(!StringUtils.isEmpty(startDate)) {
            criteria.add(Restrictions.lte("createTime", DateUtil.parseDate(startDate+= " 00:00:00"), true)).add(Restrictions.gte("createTime", DateUtil.parseDate(endDate+= " 23:59:59"), true));
        }
        if(!StringUtils.isEmpty(placeId)){
            criteria.add(Restrictions.eq("receAddres.placeId",placeId.substring(placeId.lastIndexOf("/")+1),true));
        }
        if(!StringUtils.isEmpty(username)){
            criteria.add(Restrictions.like("user.nickName",username, true));
        }
        if(!StringUtils.isEmpty(orderNo)){
            criteria.add(Restrictions.eq("orderNo",orderNo, true));
        }
        if(!StringUtils.isEmpty(driver)){
            criteria.add(Restrictions.like("driver.nickName",driver, true));
        }
        if(!StringUtils.isEmpty(labelNo)){
            criteria.add(Restrictions.like("labelNo",labelNo, true));
        }
        if(ShiroUtil.getUserType().equals(STAFF.getValue())){
            Staff staff = staffRepository.findByUser_UserId(ShiroUtil.getUserId());
            if(staff.getStaffType().equals(ProjectConfig.StaffType.COURIER.getValue())){
                criteria.add(Restrictions.eq("dispatcherId",ShiroUtil.getUserId(), true));
            }else {
                String stationId = staffRepository.findStationIdByUserId(ShiroUtil.getUserId());
                criteria.add(Restrictions.eq("pickUpStationIdNext",stationId, true));
            }
        }
        if(ShiroUtil.getUserType().equals(DRIVER.getValue())){
            criteria.add(Restrictions.eq("driverId",ShiroUtil.getUserId(), true));
        }
        return orderRepository.findAll(criteria,PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC,"orderId")));

    }


    private void jiguang(Message message,Integer userId){
        String id = userRepository.getAppId(userId);
        if(StringUtils.isEmpty(id)){
            System.out.println("推送id为空");
            return;
        }

        Map<String,String> map = new HashMap<>();
        map.put("msg",message.getMessageContent());
        map.put("title",message.getMessageTitle());
        map.put("id",id);
        System.out.println("开始推送"+map.toString());
        System.out.println("PRODUCTION"+JiGuangMessage.getPRODUCTION());

        JiGuangMessage.jpushAll(map);
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


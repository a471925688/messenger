package com.noah.solutions.system.service;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.system.entity.Order;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.view.UserOrderView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface OrderService {


    Page listPage(Integer page, Integer limit, Order order);
    List<UserOrderView>  listPage(Pageable pageable,User user);
    Page listPage(Integer page, Integer limit, String startDate, String endDate, String username,Integer orderState,String labelNo)throws Exception;
    Page listStockPage(Integer page, Integer limit, String startDate, String endDate, String username,String orderNo,String placeId, Integer orderState,String labelNo)throws Exception;
    Page listTransitPage(Integer page, Integer limit, String startDate, String endDate, String username,String drivername,String orderNo, Integer orderState,String labelNo)throws Exception;
    //根据年份查询统计订单
    List countByMonth(String year)throws Exception;
    //根据年份统计提货点订单
    List countGroupByPickUpStation(String year)throws Exception;
    void add(Order order,String userId)throws Exception;
    void update(Order order);
    void delete(String id);
    //出庫
    void sendAll(String driverId, String pickUpStationId,String userId,List<String> orderIds);
    void sendAll(String driverId, String pickUpStationId,String userId,List<String> orderIds,String labelNo);
    List<UserOrderView> transportList(User user, Pageable pageable);

    List<UserOrderView> deliverList(User user,Pageable pageable);

    List<UserOrderView> completedList(User user,Pageable pageable);

    void appointment(String orderId, String orderAppointment);

    void collect(List<String>  orderIds,String userId)throws Exception;

    void collect(List<String>  orderIds,String userId,String labelNo)throws Exception;

    void collect(String orderId,String userId)throws Exception;

    void deliver(List<String> orderIds, String dispatcherId,String userId);

    void receive(String orderId, String pickUpCode, String s);


    UserOrderView getUserOrderView(String orderId, User user);

    List<UserOrderView>  stockList(User user, PageRequest of);

    List<UserOrderView>  transitList(User user, PageRequest of);

    List<UserOrderView> packagedList(User user, PageRequest of);

    List<UserOrderView> listByState(User user,Integer state, PageRequest of);

    UserOrderView getOrderViewByUser(String recId, String orderNo,String pickUpCode, User user);

    Integer countBytime(Date firstDayOfMonth);

    Long countAll();

    Long countAmountBytime(Date firstDayOfMonth);

    Long countAmount();
}

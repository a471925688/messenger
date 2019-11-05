package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.Order;
import com.noah.solutions.system.entity.PickUpStation;
import com.noah.solutions.system.view.MonthlyReport;
import com.noah.solutions.system.view.UserOrderNewRecordView;
import com.noah.solutions.system.view.UserOrderView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> , JpaSpecificationExecutor<Order> {

    boolean   existsByOrderNo(String ordreNo);

    @Query("select new com.noah.solutions.system.view.UserOrderNewRecordView(o.orderNo,o.orderId) from Order o where o.orderState <>?1 and o.userId=?2")
    List<UserOrderNewRecordView> selectByStateIsNotAndUserId(Integer state,String userId, Pageable pageable);

    @Query("select o.pickUpStationIdNext from  Order  o where o.orderId = ?1 ")
    String findStationByOrderId(String orderId);
    @Modifying
    @Query("update Order o set o.pickUpStationIdNext=?1,o.driverId=?2,o.orderState=2 where o.orderId=?3 and o.orderState=?4")
    void sendOrder(String pickUpStationId,String driverId,String orderId,Integer orderState);

    @Query("select o.receAddres.recMode from Order o where o.orderId = ?1 and o.orderState=?2")
    Integer selectRecModeByOrderIdAndOrderState(String orderId,Integer orderState);

    @Query("select o.userId from Order o where o.orderId = ?1 ")
    String selectUserIdByOrderId(String orderId);

    @Query("select o.pickUpStationNext from Order o where o.orderId = ?1")
    PickUpStation selectDestinationByOrderIdAndOrderState(String orderId);

    @Modifying
    @Query("update Order o set o.orderState=?2 where o.orderId = ?1")
    void updateOrderState(String orderId,Integer state);

    //通過標籤號查詢所有未出庫訂單id
    @Query("select o.orderId from Order o where o.labelNo = ?1 and o.orderState=?2")
    List<String> findAllIdsByLabelNoAndState(String labelNo,Integer state);

    @Query("select o.orderId from Order o where o.labelNo = ?1")
    List<String> findAllIdsByLabelNo(String labelNo);



//    @Modifying
//    @Query("update Order o set o.orderState=?3 where o.orderId in (?1) and o.orderState=?2")
//    void updateOrderState(List<Integer> orderId,Integer oldState,Integer state);

    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o  where o.orderId = ?1 and o.userId = ?2")
    UserOrderView findUserOrderViewByOrderIdAndUserId(String orderId,String userId);

    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.orderId = ?1")
    UserOrderView findUserOrderViewByOrderId(String orderId);

    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.recId = ?1 or  o.orderNo = ?2 or o.pickUpCode = ?3")
    List<UserOrderView> findOrderViewByOrderRecIdOrOrderNo(String recId,String orderNo,String pickUpCode);

    //用戶
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.userId=?1")
    List<UserOrderView> findAllOrderViewByUserId(String userId, Pageable pageable);
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.userId=?1 and o.orderState in (?2)")
    List<UserOrderView> findAllOrderViewByUserIdAndOrderStateIn(String userId, List<Integer> states, Pageable pageable);
    //司機
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.driverId=?1")
    List<UserOrderView> findAllOrderViewByDriverId(String driverId, Pageable pageable);

    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.driverId=?1 and o.orderState in (?2)")
    List<UserOrderView> findAllOrderViewByDriverIdAndOrderStateIn(String driverId,List<Integer> states, Pageable pageable);
    //管理員
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o")
    List<UserOrderView> findAllOrderView(Pageable pageable);
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.orderState in (?1)")
    List<UserOrderView> findAllOrderViewByStateIn(List<Integer> state, Pageable pageable);
    //工作人員
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.pickUpStationIdNext=?1 or o.pickUpStationId=?1")
    List<UserOrderView> findAllOrderViewByPickUpStationId(String stationId, Pageable pageable);
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where (o.pickUpStationIdNext=?1 or o.pickUpStationId=?1) and o.orderState in (?2)")
    List<UserOrderView> findAllOrderViewByPickUpStationIdAndOrderStateIn(String stationId,List<Integer> states, Pageable pageable);
    //派送員
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where o.pickUpStationIdNext=?1 or o.dispatcherId=?1")
    List<UserOrderView> findAllOrderViewByDispatcherId(String dispatcherId, Pageable pageable);
    @Query("select  new com.noah.solutions.system.view.UserOrderView(o.orderId,o.labelNo,o.orderNo,o.goodsType,o.goodsWeight,o.goodsVolume,o.orderTokenMoney,o.pickUpCode,o.orderState,o.receAddres) from  Order o where (o.pickUpStationIdNext=?1 or o.dispatcherId=?1) and o.orderState in (?2)")
    List<UserOrderView> findAllOrderViewByDispatcherIdAndOrderStateIn(String dispatcherId,List<Integer> states, Pageable pageable);
    @Modifying
    @Query("update Order o set o.orderAppointment = ?2 where orderId = ?1")
    void updateOrderAppointment(String orderId, Date date);

    @Modifying
    @Query("update Order o set o.dispatcherId = ?2,o.orderState = 5  where orderId in (?1)")
    void deliver(List<String> orderIds, String dispatcherId);//订单派送

    @Query("select o.userId from Order o where o.orderId = ?1")
    Integer getUserId(String orderId);
    //订单月报表
    @Query(value = "SELECT DATE_FORMAT(create_time,'%m') as time,count(order_id)  money FROM tb_order  where DATE_FORMAT(create_time,'%Y')=?1  GROUP BY  DATE_FORMAT(create_time,'%Y-%m')  order by time",nativeQuery = true)
    List groupByMonth(String year);
    //获取各提货点订单报表
    @Query(value = "select p.pname,count(o.order_id) from (select  pus.pick_up_station_id pid,pus.pick_up_station_name pname from pick_up_station pus) p left join (select * from tb_order where DATE_FORMAT(tb_order.create_time,'%Y')=?1)  o on o.pick_up_station_id_next=p.pid  group by p.pid",nativeQuery = true)
    List groupByPickUpStation(String year);


    Integer countAllByCreateTimeGreaterThan(Date time);

    @Query("select sum(o.orderTokenMoney) from Order o where o.createTime>?1")
    Long countAmountBytime(Date time);

    @Query("select sum(o.orderTokenMoney) from Order o")
    Long countAmount();
}

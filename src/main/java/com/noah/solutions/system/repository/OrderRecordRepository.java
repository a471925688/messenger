package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.view.UserOrderNewRecordView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderRecordRepository extends JpaRepository<OrderRecord,String> {
    Set<OrderRecord> findAllByOrderIdOrderByIdDesc(String orderId);

    List<OrderRecord> findAllByOrderId(String orderId, Pageable pageable);

    @Query("select new OrderRecord(u.nickName,od) from OrderRecord  od  inner join com.noah.solutions.system.entity.User u on od.operatorId=u.userId  where od.orderId=?1 order by od.createTime desc,od.id desc ")
    List<OrderRecord> selectAllByOrderId(String orderId);

    OrderRecord findFirstByOrderIdOrderByCreateTimeDesc(String orderId);

////    @Query("select new com.noah.solutions.system.view.UserOrderNewRecordView(o.orderNo,or.title,or.tbExplain,or.createTime,or.courier) from OrderRecord  or inner join  com.noah.solutions.system.entity.Order o on or.orderId = o.orderId where o.orderState <> ?1")
//    @Query(value = "select new OrderRecord(u.nickName,od) from OrderRecord  od inner join (select o.order_id,o.order_no from tb_order o where o.orderState <> ?1 limit ?2,?3",nativeQuery = true)
//    List<UserOrderNewRecordView> findAllNewRecord(Integer orderState,Integer page,Integer limit);
}

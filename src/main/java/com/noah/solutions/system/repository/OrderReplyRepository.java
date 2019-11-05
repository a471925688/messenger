package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.entity.OrderReply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface OrderReplyRepository extends JpaRepository<OrderReply,String> {
    List<OrderReply> findAllByIsReadAndOrderId(Boolean isRead,String orderId,Pageable pageable);
    List<OrderReply> findAllByOrderId(String orderId,Pageable pageable);

    @Modifying
    @Query("update OrderReply od set od.isRead = true where od.orderReplyId=?1")
    void readOrderReply(String orderReplyId);



    @Modifying
    @Query("update OrderReply od set od.isRead = true where od.isMember=1")
    void readOrderReply();
}

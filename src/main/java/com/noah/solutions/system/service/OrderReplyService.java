package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.entity.OrderReply;
import com.noah.solutions.system.view.UserOrderNewRecordView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderReplyService {

    List<OrderReply> orderReply(String orderId, Boolean isRead, Pageable pageable);

    void addOrderReply(String content, String orderId);

    void readOrderReply(String orderReplyId);

    void addOrderReply(OrderReply orderReply);

    void readAllUserOrderReply();
}

package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.entity.OrderReply;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.view.UserOrderNewRecordView;
import com.noah.solutions.system.view.UserOrderView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRecordService {

    Page list(Integer page, Integer limit, OrderRecord orderRecord);
    void add(OrderRecord orderRecord);
    void delete(String id);
    void addAll(OrderRecord orderRecord, List<String> orderIds);

    void addAll(OrderRecord orderRecord,  List<String> orderIds, String labelNo);

    List<OrderRecord> listAllByOrderId(String orderId);

    List<OrderRecord> listPageByOrderId(String orderId,Pageable pageable);

    List<UserOrderNewRecordView> listAllNewRecord(Pageable pageable);

    List<OrderReply> orderReply(String orderId,Boolean isRead, Pageable pageable);


}

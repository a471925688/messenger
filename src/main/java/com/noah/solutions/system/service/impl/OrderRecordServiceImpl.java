package com.noah.solutions.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.shiro.ShiroUtil;
import com.noah.solutions.common.utils.StringUtil;
import com.noah.solutions.system.entity.OrderReply;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.repository.OrderRecordRepository;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.entity.OrderRecord;
import com.noah.solutions.system.repository.OrderReplyRepository;
import com.noah.solutions.system.repository.OrderRepository;
import com.noah.solutions.system.service.OrderRecordService;
import com.noah.solutions.system.view.UserOrderNewRecordView;
import com.noah.solutions.system.view.UserOrderView;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.noah.solutions.common.ProjectConfig.OrderState.*;

@Service
public class OrderRecordServiceImpl implements OrderRecordService {

    @Resource
    private OrderRecordRepository orderRecordRecordRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private OrderReplyRepository orderReplyRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer page, Integer limit, OrderRecord orderRecordRecord) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return orderRecordRecordRepository.findAll(Example.of(orderRecordRecord,matcher), PageRequest.of(page-1,limit));
    }



    @Override
    public List<OrderRecord> listPageByOrderId(String orderId, Pageable pageable) {
        return orderRecordRecordRepository.findAllByOrderId(orderId,pageable);
    }

    @Override
    public List<UserOrderNewRecordView> listAllNewRecord(Pageable pageable) {
        List<UserOrderNewRecordView> userOrderNewRecordViews = orderRepository.selectByStateIsNotAndUserId(COMPLETED.getValue(), ShiroUtil.getUserId()+"",pageable);
        userOrderNewRecordViews.forEach(v->{
            BeanUtils.copyNotNullProperties(orderRecordRecordRepository.findFirstByOrderIdOrderByCreateTimeDesc(v.getOrderId()),v);
        });
        return userOrderNewRecordViews;
    }

    @Override
    public List<OrderReply> orderReply(String orderId,Boolean isRead, Pageable pageable) {
        if(isRead==null){
            return orderReplyRepository.findAllByOrderId(orderId,pageable);
        }else {
            return orderReplyRepository.findAllByIsReadAndOrderId(isRead,orderId,pageable);
        }

    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Transactional
    @Override
    public void add(OrderRecord orderRecord) {
        orderRecordRecordRepository.save(orderRecord);
        if(!orderRecord.getStatus())
            orderRepository.updateOrderState(orderRecord.getOrderId(), ABNORMAL.getValue());
    }

    @Override
    @Transactional
    public void addAll(OrderRecord orderRecord, List<String> orderIds) {
        orderIds.forEach(v->{
            OrderRecord orderRecord1 = new OrderRecord();
            orderRecord1.setOrderId(v);
            BeanUtils.copyNotNullProperties(orderRecord,orderRecord1);
            orderRecordRecordRepository.save(orderRecord1);
            if(!orderRecord.getStatus())
                orderRepository.updateOrderState(v, ABNORMAL.getValue());
        });
    }

    @Override
    @Transactional
    public void addAll(OrderRecord orderRecord,  List<String> orderIds, String labelNo) {
        orderIds.addAll(orderRepository.findAllIdsByLabelNo(labelNo));
        addAll(orderRecord,orderIds);
    }

    @Override
    public List<OrderRecord> listAllByOrderId(String orderId) {
        return orderRecordRecordRepository.selectAllByOrderId(orderId);
    }


    @Transactional
    @Override
    public void delete(String id) {
        orderRecordRecordRepository.deleteById(id);
    }




    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.shiro.ShiroUtil;
import com.noah.solutions.system.entity.OrderReply;
import com.noah.solutions.system.repository.DaoUtil.Page;
import com.noah.solutions.system.repository.OrderReplyRepository;
import com.noah.solutions.system.service.OrderReplyService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.beans.Transient;
import java.util.List;

@Service
public class OrderReplyServiceImpl implements OrderReplyService {
    @Resource
    private OrderReplyRepository orderReplyRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

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

    @Override
    @Transactional
    public void addOrderReply(String content, String orderId) {
        if(ShiroUtil.getUserType().equals(ProjectConfig.UserType.MEMBER.getValue())){
            orderReplyRepository.save(new OrderReply(orderId, ShiroUtil.getUserId()+"",true,content));
        }else {
            orderReplyRepository.save(new OrderReply(orderId, ShiroUtil.getUserId()+"",false,content));
        }

    }

    @Override
    @Transactional
    public void addOrderReply(OrderReply orderReply) {
        orderReply.setUserId(ShiroUtil.getUserId()+"");
        orderReplyRepository.save(orderReply);
        orderReplyRepository.readOrderReply();
    }

    @Override
    @Transactional
    public void readAllUserOrderReply() {
        orderReplyRepository.readOrderReply();
    }

    @Override
    @Transactional
    public void  readOrderReply(String orderReplyId) {
        orderReplyRepository.readOrderReply(orderReplyId);
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


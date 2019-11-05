package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.shiro.ShiroUtil;
import com.noah.solutions.system.entity.Message;
import com.noah.solutions.system.entity.Place;
import com.noah.solutions.system.repository.MessageRepository;
import com.noah.solutions.system.repository.PlaceRepository;
import com.noah.solutions.system.service.MessageService;
import com.noah.solutions.system.service.PlaceService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    private MessageRepository messageRepository;




    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////


    @Override
    public List<Message> list(Boolean isRead, Pageable pageable) {
        if(StringUtils.isEmpty(isRead)){
            return messageRepository.findAllByUserId(ShiroUtil.getUserId()+"",pageable);
        }else {
            return messageRepository.findAllByUserIdAndIsRead(ShiroUtil.getUserId()+"",isRead,pageable);
        }

    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////

    @Override
    @Transactional
    public void read(String messageId) {
        messageRepository.readMessage(messageId,ShiroUtil.getUserId()+"");
    }



    @Override
    @Transactional
    public void insert(Integer num)throws Exception{
        for (int i=0;i<num;i++){
            Message message = new Message();
            message.setMessageType(i%1+1);
            message.setUserId("5");
            message.setMessageTitle("測試"+i);
            message.setMessageContent("測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,測試內容,"+i);
            messageRepository.save(message);
        }

    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////



    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


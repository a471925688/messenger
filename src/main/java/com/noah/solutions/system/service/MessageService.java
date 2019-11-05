package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {
    List<Message> list(Boolean isRead, Pageable pageable);

    void read(String messageId);

    void insert(Integer num)throws Exception;
}

package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.Staff;
import com.noah.solutions.system.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {
    Page list(Integer page, Integer limit, Staff staff,Integer userId);

    void add(Staff staff)throws Exception;

    void update(Staff staff)throws Exception;

    List<Staff> listDispatcherAll(String orderId);

    Page listDispatcher(Integer page, Integer limit,String nickName,String orderId);

    List<User> listDispatcher(User user);

    Integer getUserType(Integer userId);
}

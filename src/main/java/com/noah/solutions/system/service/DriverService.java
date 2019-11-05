package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DriverService {
    Page list(Integer page, Integer limit, User user);

    void add(User user);

    void update(User user);


    List<User> findAll();
}

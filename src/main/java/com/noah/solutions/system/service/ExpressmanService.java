package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.User;
import org.springframework.data.domain.Page;

public interface ExpressmanService {
    Page list(Integer page, Integer limit, User user);

    void add(User user);

    void update(User user);
}

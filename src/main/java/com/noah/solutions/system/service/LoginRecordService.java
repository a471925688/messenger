package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.LoginRecord;
import org.springframework.data.domain.Page;

public interface LoginRecordService {

    boolean add(LoginRecord loginRecord);

    Page<LoginRecord> list(int pageNum, int pageSize, String startDate, String endDate, String username);
}

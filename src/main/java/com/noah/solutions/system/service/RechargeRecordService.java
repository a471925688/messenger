package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.RechargeRecord;
import org.springframework.data.domain.Page;

public interface RechargeRecordService {
    Page listPage(Integer page, Integer limit, RechargeRecord tokenRechargeRecord);
    RechargeRecord findById(String id);
    void add(RechargeRecord tokenRechargeRecord);
    void update(RechargeRecord tokenRechargeRecord);

    Page listPage(Integer page, Integer limit, String startDate, String endDate, String username);
}

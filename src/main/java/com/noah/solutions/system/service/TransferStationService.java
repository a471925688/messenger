package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.TransferStation;

public interface TransferStationService {
    TransferStation getInfo();
    void update(TransferStation transferStation)throws Exception;
}

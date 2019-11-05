package com.noah.solutions.system.service.impl;

import com.noah.solutions.system.entity.TransferStation;
import com.noah.solutions.system.repository.TransferStationRepository;
import com.noah.solutions.system.service.TransferStationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class TransferStationServiceImpl implements TransferStationService {
    @Resource
    private TransferStationRepository transferStationRepository;


    @Override
    public TransferStation getInfo() {
        return transferStationRepository.findAll().get(0);
    }

    @Override
    @Transactional
    public void update(TransferStation transferStation) throws Exception {
        transferStation.setUpdateTime(new Date());
        transferStationRepository.save(transferStation);
    }
}

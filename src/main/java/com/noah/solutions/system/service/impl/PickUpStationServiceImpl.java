package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.repository.PickUpStationRepository;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.entity.PickUpStation;
import com.noah.solutions.system.repository.StaffRepository;
import com.noah.solutions.system.service.PickUpStationService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.noah.solutions.common.ProjectConfig.UserType.STAFF;

@Service
public class PickUpStationServiceImpl implements PickUpStationService {

    @Resource
    private PickUpStationRepository pickUpStationRepository;

    @Resource
    private StaffRepository staffRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer page, Integer limit, PickUpStation pickUpStation) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return pickUpStationRepository.findAll(Example.of(pickUpStation,matcher), PageRequest.of(page-1,limit));
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


//    @Override
//    public void add(String name,String phone,Float pointx,Float pointy,String pickUpStationAddr,String recRemarks) {
//        pickUpStationRepository.save(new PickUpStation(name,phone,pointx,pointy,pickUpStationAddr,recRemarks));
//    }



    @Transactional
    @Override
    public void add(PickUpStation pickUpStation) {
        pickUpStationRepository.save(pickUpStation);
    }

    @Transactional
    @Override
    public void update(PickUpStation pickUpStation) {
        PickUpStation pickUpStation1 = pickUpStationRepository.getOne(pickUpStation.getPickUpStationId());
        BeanUtils.copyNotNullProperties(pickUpStation,pickUpStation1);
        pickUpStation1.setUpdateTime(new Date());
        pickUpStationRepository.save(pickUpStation1);
    }


    @Transactional
    @Override
    public void delete(String id) {
        pickUpStationRepository.deleteById(id);
    }

    @Override
    public List<PickUpStation> listAll(User user) {
        PickUpStation pickUpStation = new PickUpStation();
        if(user.getType().equals(STAFF.getValue())){
            pickUpStation.setPickUpStationId(staffRepository.findStationIdByUserId(user.getUserId()));
        }
        ExampleMatcher matcher = ExampleMatcher.matching();
        return pickUpStationRepository.findAll(Example.of(pickUpStation,matcher));
    }
    @Override
    public List<PickUpStation> listPage(User user,Pageable pageable) {
//        PickUpStation pickUpStation = new PickUpStation();
//        if(user.getType()== STAFF.getValue()){
//            pickUpStation.setPickUpStationId(staffRepository.findStationIdByUserId(user.getUserId()));
//        }
        return pickUpStationRepository.findAll(pageable).getContent();
    }
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.PickUpStation;
import com.noah.solutions.system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PickUpStationService {

//    void add(String name,String phone,Float pointx,Float pointy,String pickUpStationAddr,String recRemarks);
    Page list(Integer page, Integer limit, PickUpStation pickUpStation);
    void add(PickUpStation pickUpStation);
    void update(PickUpStation pickUpStation);
    void delete(String id);

    List<PickUpStation> listAll(User user);

    List<PickUpStation> listPage(User user, Pageable pageable);



}

package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.ReceAddres;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReceAddresService {

    void add(String recName,String recPhone,String placeId,String pickUpStationId,String recDetailedAddr,String recRemarks,String userId);
    List<ReceAddres> listPage(Integer recMode,String userId, Pageable pageable);
    void delete(String id,String userId);

    void edit(ReceAddres receAddres)throws Exception;

}

package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.repository.ReceAddresRepository;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.system.entity.ReceAddres;
import com.noah.solutions.system.service.ReceAddresService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ReceAddresServiceImpl implements ReceAddresService {

    @Resource
    private ReceAddresRepository receAddresRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public List<ReceAddres> listPage(Integer recMode,String userId,Pageable pageable) {
        List<ReceAddres>  receAddres= null;
        if(recMode!=null){
            receAddres = receAddresRepository.getAllByUserIdAndRecMode(userId,recMode,pageable);
        }else {
            receAddres = receAddresRepository.getAllByUserId(userId,pageable);
        }
        return receAddres;
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    public void add(String recName,String recPhone,String placeId,String pickUpStationId,String recDetailedAddr,String recRemarks,String userId) {
        if(StringUtils.isEmpty(placeId)){
            placeId=null;
        }
        if(StringUtils.isEmpty(pickUpStationId)){
            pickUpStationId=null;
        }
        if(StringUtils.isEmpty(placeId)&&StringUtils.isEmpty(pickUpStationId)){
            throw new CustormException("pickUpStationId,placeId不能同时为空",0);
        }
        if(!StringUtils.isEmpty(placeId)&&StringUtils.isEmpty(recDetailedAddr)){
            throw new CustormException("自提recDetailedAddr参数不能为空",0);
        }
        if(!StringUtils.isEmpty(placeId)&&!StringUtils.isEmpty(pickUpStationId)){
            throw new CustormException("pickUpStationId,placeId只能选其一",0);
        }
        ReceAddres receAddres = new ReceAddres(recName,recPhone,placeId,pickUpStationId,recDetailedAddr,recRemarks,userId);
        if(!StringUtils.isEmpty(placeId)){
            receAddres.setRecMode(ProjectConfig.ReceAddresMode.DELIVERYABOVE.getValue());
        }else {
            receAddres.setRecMode(ProjectConfig.ReceAddresMode.SELFMENTION.getValue());
        }
        receAddresRepository.save(receAddres);
    }



    @Override
    public void edit(ReceAddres receAddres)throws Exception {
        ReceAddres oldReceAddres = receAddresRepository.findByRecIdAndUserId(receAddres.getRecId(),receAddres.getUserId());
        if(oldReceAddres==null){
            throw new CustormException("地址id異常或與當前用戶不匹配",1);
        }
        if(StringUtils.isEmpty(receAddres.getPlaceId())&&StringUtils.isEmpty(receAddres.getPickUpStationId())){
            throw new CustormException("pickUpStationId,placeId不能同时为空",0);
        }
        if(!StringUtils.isEmpty(receAddres.getPlaceId())&&StringUtils.isEmpty(receAddres.getRecDetailedAddr())){
            throw new CustormException("自提recDetailedAddr参数不能为空",0);
        }
        if(!StringUtils.isEmpty(receAddres.getPlaceId())&&!StringUtils.isEmpty(receAddres.getPickUpStationId())){
            throw new CustormException("pickUpStationId,placeId只能选其一",0);
        }
        if(StringUtils.isEmpty(receAddres.getPlaceId())){
            receAddres.setPlaceId(null);
        }
        if(StringUtils.isEmpty(receAddres.getPickUpStationId())){
            receAddres.setPickUpStationId(null);
        }
        receAddres.setRecCreateTime(oldReceAddres.getRecCreateTime());
        receAddres.setRecUpdateTime(new Date());
        receAddres.setIsDelete(oldReceAddres.getIsDelete());
        if(!StringUtils.isEmpty(receAddres.getPlaceId())){
            receAddres.setRecMode(ProjectConfig.ReceAddresMode.DELIVERYABOVE.getValue());
        }else {
            receAddres.setRecMode(ProjectConfig.ReceAddresMode.SELFMENTION.getValue());
        }
        receAddresRepository.save(receAddres);
    }


    @Transactional
    @Override
    public void delete(String id,String userId) {
        receAddresRepository.delete(id,userId);
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


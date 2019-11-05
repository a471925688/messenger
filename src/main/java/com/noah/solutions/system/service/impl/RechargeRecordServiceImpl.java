package com.noah.solutions.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.noah.solutions.system.repository.DaoUtil.Criteria;
import com.noah.solutions.system.repository.DaoUtil.Criterion;
import com.noah.solutions.system.repository.DaoUtil.Restrictions;
import com.noah.solutions.system.repository.DaoUtil.SimpleExpression;
import com.noah.solutions.system.repository.RechargeRecordRepository;
import com.noah.solutions.system.repository.UserRepository;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.common.utils.DateUtil;
import com.noah.solutions.system.entity.RechargeRecord;
import com.noah.solutions.system.service.RechargeRecordService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class RechargeRecordServiceImpl implements RechargeRecordService {

    @Resource
    private RechargeRecordRepository rechargeRecordRepository;

    @Resource
    private UserRepository userRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page listPage(Integer page, Integer limit, RechargeRecord rechargeRecord) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("createTime", ExampleMatcher.GenericPropertyMatchers.startsWith());;
        return rechargeRecordRepository.findAll(  Example.of(rechargeRecord,matcher), PageRequest.of(page-1,limit));
    }


    @Override
    public Page listPage(Integer page, Integer limit, String startDate, String endDate, String username){
        Criteria criteria = new Criteria();
        if(!StringUtils.isEmpty(startDate)) {
            criteria.add(Restrictions.lte("createTime", DateUtil.parseDate(startDate+= " 00:00:00"), true)).add(Restrictions.gte("createTime", DateUtil.parseDate(endDate+= " 23:59:59"), true));
        }
        if(!StringUtils.isEmpty(username)){
            criteria.add(new SimpleExpression("user.username",username, Criterion.Operator.LIKE));
        }
        return rechargeRecordRepository.findAll(criteria,PageRequest.of(page-1,limit,new Sort(Sort.Direction.DESC,"rechargeId")));
    }

    @Override
    public RechargeRecord findById(String id) {
        return rechargeRecordRepository.getOne(id);
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(RechargeRecord rechargeRecord) {
        rechargeRecordRepository.save(rechargeRecord);
        userRepository.addTokenMoney(rechargeRecord.getRechargeAmount(),rechargeRecord.getUserId());
    }





    @Override
    @Transactional
    public void update(RechargeRecord rechargeRecord) {
        RechargeRecord oldRechargeRecord = rechargeRecordRepository.getOne(rechargeRecord.getRechargeId());
        BeanUtils.copyNotNullProperties(rechargeRecord,oldRechargeRecord);
        rechargeRecordRepository.save(oldRechargeRecord);
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


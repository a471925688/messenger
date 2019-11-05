package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.entity.BannerType;
import com.noah.solutions.system.repository.BannerTypeRepository;
import com.noah.solutions.system.service.BannerTypeService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BannerTypeServiceImpl implements BannerTypeService {

    @Resource
    private BannerTypeRepository bannerTypeRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer page, Integer limit, BannerType bannerType) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("user.username", ExampleMatcher.GenericPropertyMatchers.contains());
        return bannerTypeRepository.findAll(Example.of(bannerType,matcher), PageRequest.of(page-1,limit));
    }
    @Override
    public List<BannerType> listAll() {
        return bannerTypeRepository.findAll();
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(BannerType bannerType)throws Exception {
        bannerTypeRepository.save(bannerType);
    }


    @Override
    @Transactional
    public void update(BannerType bannerType)throws Exception {
        BannerType oldBannerType = bannerTypeRepository.getOne(bannerType.getTypeId());
        BeanUtils.copyNotNullProperties(bannerType,oldBannerType);
        bannerTypeRepository.save(oldBannerType);
    }



    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


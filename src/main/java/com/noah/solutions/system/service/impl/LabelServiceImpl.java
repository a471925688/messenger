package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.utils.UUIDUtil;
import com.noah.solutions.system.entity.Label;
import com.noah.solutions.system.repository.LabelRepository;
import com.noah.solutions.system.service.LabelService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {

    @Resource
    private LabelRepository labelRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////

    @Override
    public List<Label> listAll(Integer status) {
        return labelRepository.findAllByStatus(status);
    }
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////



    @Override
    @Transactional
    public String getNewNo() {
        String no = UUIDUtil.randomUUID8();
        labelRepository.save(new Label(no));
        return no;
    }

    @Transactional
    @Override
    public void updateStatus(String no, Integer status) {
        labelRepository.updateStatus(no,status);
    }

    @Override
    @Transactional
    public void add(Label label) {
        label.setNo(UUIDUtil.randomUUID8());
        labelRepository.save(label);
    }

    @Override
    @Transactional
    public void delete(String no) {
        labelRepository.delByNo(no);
    }

    @Override
    public Page list(Integer page, Integer limit, Label label) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("no", ExampleMatcher.GenericPropertyMatchers.contains());
        return labelRepository.findAll(Example.of(label,matcher), PageRequest.of(page-1,limit));
    }




    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.Label;
import com.noah.solutions.system.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LabelService {
    List<Label> listAll(Integer status);
    //获取新的标签号
    String getNewNo();
    //跟新标签状态
    void updateStatus(String no,Integer status);

    void add(Label label);

    void delete(String no);

    Page list(Integer page, Integer limit, Label label);

}

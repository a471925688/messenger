package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.system.repository.OrderRepository;
import com.noah.solutions.system.repository.UserRepository;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.common.shiro.EndecryptUtil;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.service.MemberService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;

import static com.noah.solutions.common.ProjectConfig.UserType.MEMBER;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private UserRepository userRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer page, Integer limit, User user) {
        user.setType(MEMBER.getValue());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nickName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());
        return userRepository.findAll(Example.of(user,matcher), PageRequest.of(page-1,limit));
    }


    @Override
    public Integer countBytime(Date time) {
        return userRepository.countAllByCreateTimeGreaterThanAndType(time,MEMBER.getValue());
    }

    @Override
    public Integer countAll() {
        return userRepository.countAllByType(MEMBER.getValue());
    }

    @Override
    public Integer countByActive(Date time) {
        return userRepository.countByActiveUser(time);
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    public void add(User user) {
        user.setPassword("123456");
        if (userRepository.existsByUsername(user.getUsername())||userRepository.existsByPhone(user.getPhone())) {
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        }
        user.setType(MEMBER.getValue());
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword(), user.getUsername(), 3));
        user.setState(0);
        user.setCreateTime(new Date());
        user.setTokenMoney((double) 0);
        if(StringUtils.isEmpty(user.getAvatar()))
            user.setAvatar("head.png");
        userRepository.save(user);
    }


    @Override
    public void update(User user) {
        User oldUser = userRepository.findByUserId(user.getUserId());
        BeanUtils.copyNotNullProperties(user,oldUser);
        oldUser.setUpdateTime(new Date());
        userRepository.save(oldUser);
    }

    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


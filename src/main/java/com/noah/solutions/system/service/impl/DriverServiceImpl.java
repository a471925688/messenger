package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.common.shiro.EndecryptUtil;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.entity.UserRole;
import com.noah.solutions.system.repository.RoleRepository;
import com.noah.solutions.system.repository.UserRepository;
import com.noah.solutions.system.repository.UserRoleRepository;
import com.noah.solutions.system.service.DriverService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.noah.solutions.common.ProjectConfig.UserType.DRIVER;

@Service
public class DriverServiceImpl implements DriverService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private RoleRepository roleRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer page, Integer limit, User user) {
        user.setType(DRIVER.getValue());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nickName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());
        return userRepository.findAll(Example.of(user,matcher), PageRequest.of(page-1,limit));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAllByType(DRIVER.getValue());
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
        user.setType(DRIVER.getValue());
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword(), user.getUsername(), 3));
        user.setState(0);
        user.setCreateTime(new Date());
        user.setTokenMoney((double) 0);
        if(StringUtils.isEmpty(user.getAvatar()))
            user.setAvatar("head.png");
        user = userRepository.saveAndFlush(user);
        userRoleRepository.save(new UserRole(user.getUserId(),roleRepository.findIdByUsername("司機")));
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


package com.noah.solutions.system.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.common.shiro.EndecryptUtil;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.common.utils.DateUtil;
import com.noah.solutions.system.entity.Staff;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.entity.UserRole;
import com.noah.solutions.system.repository.*;
import com.noah.solutions.system.repository.DaoUtil.Criteria;
import com.noah.solutions.system.repository.DaoUtil.Restrictions;
import com.noah.solutions.system.service.OrderService;
import com.noah.solutions.system.service.StaffService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.noah.solutions.common.ProjectConfig.StaffType.COURIER;
import static com.noah.solutions.common.ProjectConfig.UserType.STAFF;

@Service
public class StaffServiceImpl implements StaffService {

    @Resource
    private StaffRepository staffRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private UserRoleRepository userRoleRepository;


    @Resource
    private OrderRepository orderRepository;


    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer page, Integer limit, Staff staff,Integer userId) {
        staff.getUser().setType(STAFF.getValue());
        staff.setPickUpStationId(staffRepository.findStationIdByUserId(userId));
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("user.username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("user.nickName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("user.phone", ExampleMatcher.GenericPropertyMatchers.contains());
        return staffRepository.findAll(Example.of(staff,matcher), PageRequest.of(page-1,limit));
    }



    @Override
    public Page listDispatcher(Integer page, Integer limit,String nickName,String orderId) {
        Criteria criteria = new Criteria();
        String pickUpStationId = orderRepository.findStationByOrderId(orderId);
        criteria.add(Restrictions.eq("user.state",0,true));
        criteria.add(Restrictions.eq("staffType", COURIER.getValue(),true));
        if(!StringUtils.isEmpty(nickName)) {
            criteria.add(Restrictions.like("user.nickName",nickName, true));
        }
        if(!StringUtils.isEmpty(pickUpStationId)) {
            criteria.add(Restrictions.eq("pickUpStationId",pickUpStationId, true));
        }
        return staffRepository.findAll(criteria,PageRequest.of(page-1,limit));
    }
    @Override
    public List<User> listDispatcher(User user){
        if(!user.getType().equals(STAFF.getValue()))
            throw new CustormException("该接口只支持提货点工作人员",1);
        return staffRepository.selectByUserId(user.getUserId(), COURIER.getValue());
    }

    @Override
    public Integer getUserType(Integer userId) {
        Integer userType = staffRepository.getType(userId);
        if(userType==2){
            return 5;
        }else {
            return 3;
        }
    }

    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(Staff staff)throws Exception {
        User  user =  staff.getUser();
        user.setPassword("123456");
        if (userRepository.existsByUsername(user.getUsername())||userRepository.existsByPhone(user.getPhone())) {
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        }
        user.setType(STAFF.getValue());
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword(), user.getUsername(), 3));
        user.setState(0);
        user.setCreateTime(new Date());
        user.setTokenMoney((double) 0);
        if(org.springframework.util.StringUtils.isEmpty(user.getAvatar()))
            user.setAvatar("head.png");
        staff.setUser(userRepository.saveAndFlush(user));
        staff = staffRepository.saveAndFlush(staff);
        if(staff.getStaffType()==ProjectConfig.StaffType.OPERATOR.getValue()){
            userRoleRepository.save(new UserRole(user.getUserId(),roleRepository.findIdByUsername("操作員")));
        }else {
            userRoleRepository.save(new UserRole(user.getUserId(),roleRepository.findIdByUsername("派送員")));
        }

    }


    @Override
    @Transactional
    public void update(Staff staff)throws Exception {
        Staff oldStaff = staffRepository.findByStaffId(staff.getStaffId());
        if(!oldStaff.getUser().getUsername().equals(staff.getUser().getUsername())){
            if (userRepository.existsByUsername(staff.getUser().getUsername())) {
                throw new CustormException(CodeAndMsg.USER_EDITERROR);
            }
        }
        if(!oldStaff.getUser().getPhone().equals(staff.getUser().getPhone())){
            if (userRepository.existsByPhone(staff.getUser().getPhone())) {
                throw new CustormException(CodeAndMsg.USER_EDITERROR);
            }
        }
        BeanUtils.copyNotNullProperties(staff.getUser(),oldStaff.getUser());
        staff.setUser(null);
        BeanUtils.copyNotNullProperties(staff,oldStaff);
        User oldUser = oldStaff.getUser();
        oldUser.setUpdateTime(new Date());
        userRoleRepository.deleteAllByUserId(oldUser.getUserId());
        if(staff.getStaffType()==ProjectConfig.StaffType.OPERATOR.getValue()){
            userRoleRepository.save(new UserRole(oldUser.getUserId(),roleRepository.findIdByUsername("操作員")));
        }else {
            userRoleRepository.save(new UserRole(oldUser.getUserId(),roleRepository.findIdByUsername("派送員")));
        }
        staffRepository.save(oldStaff);
    }

    @Override
    public List<Staff> listDispatcherAll(String orderId) {
        String pickUpStationId = orderRepository.findStationByOrderId(orderId);
        return staffRepository.findAllByStaffTypeAndPickUpStationId(COURIER.getValue(),pickUpStationId);
    }


    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////

    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


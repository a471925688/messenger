package com.noah.solutions.system.service.impl;


import com.noah.solutions.system.repository.RoleAuthoritiesRepository;
import com.noah.solutions.system.repository.RoleRepository;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.entity.Role;
import com.noah.solutions.system.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
//    @Autowired
//    private RoleMapper roleMapper;

//    @Autowired
//    private RoleAuthoritiesMapper roleAuthoritiesMapper;


    @Resource
    private RoleRepository roleRepository;

    @Resource
    private RoleAuthoritiesRepository roleAuthoritiesRepository;

    @Override
    public List<Role> getByUserId(Integer userId) {
        return roleRepository.selectByUserId(userId);
    }

    @Override
    public List<Role> getAllRole(Integer isDelete) {
        return roleRepository.findAllByIsDelete(isDelete);
    }


    @Override
    public boolean add(Role role) {
        role.setCreateTime(new Date());
        role.setIsDelete(0);
        roleRepository.save(role);
        return true;
    }

    @Override
    public boolean update(Role role) {
        Role oldRole = roleRepository.getOne(role.getRoleId());
        BeanUtils.copyNotNullProperties(role,oldRole);
        roleRepository.save(oldRole);
        return  true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateState(Integer roleId, int isDelete) {
        if (isDelete != 0 && isDelete != 1) {
            throw new CustormException("isDelete值需要在[0,1]中",1);
        }
        Role oldRole = roleRepository.getOne(roleId);
        oldRole.setIsDelete(isDelete);
        roleRepository.save(oldRole);
        roleAuthoritiesRepository.deleteAllByRoleId( roleId);
        return true;
    }

    @Override
    public Role getById(Integer roleId) {
        return roleRepository.getOne(roleId);
    }

    @Override
    public boolean delete(Integer roleId) {
        roleRepository.deleteById(roleId);
        return  true;
    }
}

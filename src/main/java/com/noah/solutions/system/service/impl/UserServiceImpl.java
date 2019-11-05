package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.shiro.ShiroUtil;
import com.noah.solutions.system.repository.CaptchaRepository;
import com.noah.solutions.system.repository.RoleRepository;
import com.noah.solutions.system.repository.UserRepository;
import com.noah.solutions.system.repository.UserRoleRepository;
import com.noah.solutions.system.thirdPartyInterface.FeigeMessage;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.common.shiro.EndecryptUtil;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.entity.Captcha;
import com.noah.solutions.system.entity.Role;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.entity.UserRole;
import com.noah.solutions.system.service.UserService;
import com.noah.solutions.system.thirdPartyInterface.UFOMessage;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static com.noah.solutions.common.ProjectConfig.UserType.ADMIN;
import static com.noah.solutions.common.exception.CodeAndMsg.*;

@Service
public class UserServiceImpl implements UserService {
//    @Autowired
//    private UserMapper userMapper;
//    @Autowired
//    private UserRoleMapper userRoleMapper;


    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private UserRoleRepository userRoleRepository;

    @Resource
    private CaptchaRepository captchaRepository;


    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> list(int pageNum, int pageSize, User user) {
        user.setType(ADMIN.getValue());
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("nickName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.contains());
        Page page = userRepository.findAll(Example.of(user,matcher),PageRequest.of(pageNum-1,pageSize));
        List<User> userList = page.getContent();
        userList.forEach(v->v.setRoles(roleRepository.selectByUserId(v.getUserId())));
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean add(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        }
        user.setType(ADMIN.getValue());
        user.setPassword(EndecryptUtil.encrytMd5(user.getPassword(), user.getUsername(), 3));
        user.setState(0);
        user.setCreateTime(new Date());
        if(StringUtils.isEmpty(user.getAvatar()))
            user.setAvatar("head.png");
        userRepository.save(user);
        List<Integer> roleIds = getRoleIds(user.getRoles());
        roleIds.forEach(v->{
            userRoleRepository.save(new UserRole(user.getUserId(),v));
        });
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean update(User user) {
        User oldUser = userRepository.findByUserId(user.getUserId());
        BeanUtils.copyNotNullProperties(user,oldUser);
        userRepository.save(oldUser);
        userRoleRepository.deleteAllByUserId( oldUser.getUserId());
        List<Integer> roleIds = getRoleIds(user.getRoles());
        roleIds.forEach(v->{
            userRoleRepository.save(new UserRole(user.getUserId(),v));
        });
        return true;
    }

    /**
     * 添加用户角色
     */
    private List<Integer> getRoleIds(List<Role> roles) {
        List<Integer> rs = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                rs.add(role.getRoleId());
            }
        }
        return rs;
    }

    @Override
    public boolean updateState(Integer userId, int state) throws Exception {
        if (state != 0 && state != 1) {
            throw new CustormException("state值需要在[0,1]中",1);
        }
        User user = userRepository.findByUserId(userId);
        user.setState(state);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean updatePsw(Integer userId, String username, String password) {
        User user = userRepository.findByUserId(userId);
        user.setPassword(EndecryptUtil.encrytMd5(password, username, 3));
        userRepository.save(user);
        return true;
    }

    @Override
    public User getById(Integer userId) {
        return userRepository.findByUserId(userId);
    }

    @Override
    public boolean delete(Integer userId) {
        userRepository.deleteById(userId);
        return true;
    }

    private List<Integer> getUserIds(List<User> userList) {
        List<Integer> userIds = new ArrayList<>();
        for (User one : userList) {
            userIds.add(one.getUserId());
        }
        return userIds;
    }



    //註冊用戶
    @Override
    @javax.transaction.Transactional
    public User registerUser(String areaCode,String phone,String verificationCode,String nickname,String passWord)throws Exception{
        checkVerificationCode(phone,verificationCode);
        User user = new User();
        //判断手機號是否重复
        if(userRepository.existsByPhone(phone))
            throw new CustormException(CodeAndMsg.USER_ADDERROR);
        //保存用户信息
        user.setUsername(phone);
        user.setSex("保密");
        user.setType(2);
        user.setPhone(areaCode+phone);
        user.setNickName(nickname);
        user.setPassword(EndecryptUtil.encrytMd5(passWord, user.getUsername(), 3));
        return userRepository.saveAndFlush(user);
    }


    //修改密码
    @Override
    @javax.transaction.Transactional
    public void modifyPassword(String phone,String verificationCode, String passWord)throws Exception{
        checkVerificationCode(phone,verificationCode);
        User user = userRepository.findByPhone(phone);
        //账号是否存在
        if(user==null)
            throw new CustormException(CodeAndMsg.USER_NOTFINDUSERNAME);
        //保存用户信息
        user.setPassword(EndecryptUtil.encrytMd5(passWord, user.getUsername(), 3));
        userRepository.save(user);
    }
    //修改密码
    @Override
    @javax.transaction.Transactional
    public void modifyPassword(String oldPassWord, String newPassWord,User user)throws Exception{
        User oldUser = userRepository.getOne(user.getUserId());
        System.out.println(EndecryptUtil.encrytMd5(oldPassWord, user.getUsername(), 3));
        if(!EndecryptUtil.encrytMd5(oldPassWord, user.getUsername(), 3).equals(user.getPassword()))
            throw new CustormException("密碼錯誤【舊密碼輸入錯誤】",1);
        //保存用户信息
        oldUser.setPassword(EndecryptUtil.encrytMd5(newPassWord, oldUser.getUsername(), 3));
        userRepository.save(oldUser);
    }

    //修改暱稱
    @Override
    @javax.transaction.Transactional
    public void modifyNick(String nickName)throws Exception{
        userRepository.updateNickName(ShiroUtil.getUserId(),nickName);
    }

    @Override
    @Transactional
    public void savHeadPortrait(String fileName) {
        userRepository.updateAvatar(ShiroUtil.getUserId(),fileName);
    }
    //保存app端id
    @Override
    @Transactional
    public void updateAppId(String appId) {
        userRepository.updateAppId(ShiroUtil.getUserId(),appId);
    }


    //獲取驗證碼
    @Override
    @javax.transaction.Transactional
    public void getCaptcha(String phone) {
        if(StringUtils.isEmpty(phone))
            throw new CustormException(USER_PHONEISNOT);
//        if(userRepository.existsByPhone(phone))
//            throw new CustormException(USER_GETCAPTCHAERROR);
        String captcha = String.valueOf((new Random().nextInt(899999) + 100000));
        UFOMessage.sendCode(captcha,phone);
        phone = phone.substring(phone.indexOf(".")+1);
        captchaRepository.save(new Captcha(phone,captcha,new Date().getTime()+1000*60*5));
    }



    private void checkVerificationCode(String phone,String verificationCode)throws Exception{
        Captcha captcha = captchaRepository.findByCaptchaPhoneAndCaptchaData(phone,verificationCode);
        if(captcha==null)
            throw new CustormException(USER_CAPTCHAERROR);
        if(captcha.getCaptchaExpirationDate()<new Date().getTime())
            throw  new CustormException(USER_CAPTCHATIMEOUT);
    }
}

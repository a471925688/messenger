package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.common.JsonResult;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.common.shiro.EndecryptUtil;
import com.noah.solutions.system.entity.Role;
import com.noah.solutions.system.entity.User;
import com.noah.solutions.system.service.RoleService;
import com.noah.solutions.system.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.noah.solutions.common.exception.CodeAndMsg.SUCESS;

/**
 * 用户管理
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @RequiresPermissions("user:view")
    @RequestMapping
    public String user(Model model) {
        List<Role> roles = roleService.getAllRole(1);
        model.addAttribute("roles", roles);
        return "system/user.html";
    }

    @RequestMapping("/editForm")
    public String addUser(Model model) {
        List<Role> roles = roleService.getAllRole(0);
        model.addAttribute("roles", roles);
        return "system/user_form.html";
    }

    /**
     * 查询用户列表
     */
    @RequiresPermissions("user:view")
    @ResponseBody
    @RequestMapping("/list")
    public JSONObject list(@RequestParam(value = "page",defaultValue = "1") Integer page,@RequestParam(value = "limit",defaultValue = "10")  Integer limit, User user) {
        return SUCESS.pageData(userService.list(page, limit, user));
    }

    /**
     * 添加用户
     **/
    @RequiresPermissions("user:add")
    @ResponseBody
    @RequestMapping("/add")
    public JsonResult add(User user, String roleId) throws Exception{
        user.setRoles(getRoles(roleId));
        user.setPassword("123456");
        if (userService.add(user)) {
            return JsonResult.ok("添加成功");
        } else {
            return JsonResult.error("添加失败");
        }
    }

    /**
     * 修改用户
     **/
    @RequiresPermissions("user:edit")
    @ResponseBody
    @RequestMapping("/update")
    public JsonResult update(User user, String roleId) {
        user.setRoles(getRoles(roleId));
        if (userService.update(user)) {
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.error("修改失败");
        }
    }

    private List<Role> getRoles(String roleStr) {
        if(StringUtils.isEmpty(roleStr)){
            return null;
        }
        List<Role> roles = new ArrayList<>();
        String[] split = roleStr.split(",");
        for (String t : split) {
            if (t.equals("1")) {
                throw new CustormException("不能添加超级管理员",1);
            }
            roles.add(new Role(Integer.parseInt(t)));
        }
        return roles;
    }

    /**
     * 修改用户状态
     **/
    @RequiresPermissions("user:delete")
    @ResponseBody
    @RequestMapping("/updateState")
    public JsonResult updateState(Integer userId, Integer state)throws Exception {
        if (userService.updateState(userId, state)) {
            return JsonResult.ok();
        } else {
            return JsonResult.error();
        }
    }

    /**
     * 修改自己密码
     **/
    @ResponseBody
    @RequestMapping("/updatePsw")
    public JsonResult updatePsw(String oldPsw, String newPsw) {
        if ("admin".equals(getUser().getUsername())) {
            return JsonResult.error("演示账号admin关闭该功能");
        }
        String finalSecret = EndecryptUtil.encrytMd5(oldPsw, getUserName(), 3);
        if (!finalSecret.equals(getUser().getPassword())) {
            return JsonResult.error("原密码输入不正确");
        }
        if (userService.updatePsw(getUserId(), getUserName(), newPsw)) {
            return JsonResult.ok("修改成功");
        } else {
            return JsonResult.error("修改失败");
        }
    }

    /**
     * 重置密码
     **/
    @RequiresPermissions("user:edit")
    @ResponseBody
    @RequestMapping("/restPsw")
    public JsonResult resetPsw(Integer userId) {
        User byId = userService.getById(userId);
        if (userService.updatePsw(userId, byId.getUsername(), "123456")) {
            return JsonResult.ok("重置成功");
        } else {
            return JsonResult.error("重置失败");
        }
    }


}

package com.noah.solutions.system.appcontroller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.BaseController;
import com.noah.solutions.common.JsonResult;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.exception.CodeAndMsg;
import com.noah.solutions.common.exception.CustormException;
import com.noah.solutions.common.shiro.ShiroSessionManager;
import com.noah.solutions.common.utils.StringUtil;
import com.noah.solutions.common.utils.UserAgentGetter;
import com.noah.solutions.system.entity.*;
import com.noah.solutions.system.service.*;
import io.swagger.annotations.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.noah.solutions.common.exception.CodeAndMsg.*;

@RestController
@RequestMapping("/app/user")
@Api(value = "用户api", tags = "user")
public class AppUserController extends BaseController {

    /*
     * 图片命名格式
     */
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMddHHmmss";


    @Autowired
    private UserService userService;
    @Autowired
    private LoginRecordService loginRecordService;
    @Autowired
    private ReceAddresService receAddresService;
    @Autowired
    private RechargeRecordService rechargeRecordService;
    @Autowired
    private StaffService staffService;



    @PostMapping("/userInfo.do")
    @ApiOperation(value = "獲取用戶信息", notes = "")
    public JSONObject getUserInfo(){
        return SUCESS.getJSON(userService.getById(getUserId()));
    }



    /**
     * 註冊用戶
     * @param phone
     * @param passWord
     * @param verificationCode
     * @return
     * @throws Exception
     */
    @PostMapping("/registUser.do")
    @ApiOperation(value = " 註冊用戶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode", value = "區號", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "電話號碼", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "nickname", value = "昵称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "passWord", value = "密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "verificationCode", value = "验证码", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject registUser(String areaCode,String phone,String passWord,String nickname,String verificationCode)throws Exception{
        userService.registerUser(areaCode,phone,verificationCode,nickname,passWord);
        return SUCESS.getJSON();
    }


    /**
     * 修改密码
     * @param phone
     * @param passWord
     * @param verificationCode
     * @return
     * @throws Exception
     */
    @PostMapping("/modifyPassword.do")
    @ApiOperation(value = " 修改密码(通過驗證碼)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "電話號碼", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "passWord", value = "新密码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "verificationCode", value = "验证码",required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject modifyPassword(@RequestParam("phone") String phone,@RequestParam("passWord")String passWord,@RequestParam("verificationCode")String verificationCode)throws Exception{
        userService.modifyPassword(phone,verificationCode,passWord);
        return HANDLESUCESS.getJSON();
    }


    /**
     * 修改密码
     * @param oldPassWord
     * @param newPassWord
     * @return
     * @throws Exception
     */
    @PostMapping("/modifyPasswordByOld.do")
    @ApiOperation(value = " 修改密码（通過舊密碼）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPassWord", value = "舊密碼", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "newPassWord", value = "新密码", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject modifyPassword(@RequestParam("oldPassWord") String oldPassWord,@RequestParam("newPassWord")String newPassWord)throws Exception{
        userService.modifyPassword(oldPassWord,newPassWord,getUser());
        return HANDLESUCESS.getJSON();
    }

    /**
     * 修改暱稱
     * @param nickName
     * @return
     * @throws Exception
     */
    @PostMapping("/modifyNick.do")
    @ApiOperation(value = " 修改暱稱")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject modifyNick(String nickName)throws Exception{
        userService.modifyNick(nickName);
        return HANDLESUCESS.getJSON();
    }

    /*
     * 上传头像图片
     */
    @PostMapping(value = "/uploadImage.do",consumes = "multipart/*",headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传头像")
    public JSONObject uploadImg(@ApiParam(value = "上传头像",required = true) MultipartFile uploadFile, HttpServletRequest request) throws Exception{
        String fileName = null;
        try {
            if (uploadFile == null) {
                request.setCharacterEncoding("UTF-8");
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                //** 页面控件的文件流* *//*
                List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
                Map map =multipartRequest.getFileMap();
                for (Iterator i = map.keySet().iterator(); i.hasNext();) {
                    Object obj = i.next();
                    multipartFiles.add((MultipartFile) map.get(obj));
                }
                uploadFile = multipartFiles.get(0);
            }
           fileName  = uploadFile.getOriginalFilename();
            if(fileName.contains("/")){
                fileName = fileName.substring(fileName.lastIndexOf("/"));
            }
            DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
            fileName = df.format(new Date()) +"_@"+fileName.replace("&"," ");
            File file = new File(ProjectConfig.FILE_IMAGE_DIRECTORY , fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            uploadFile.transferTo(file);
        } catch (Exception e) {
            System.out.println("上傳失敗");
            System.out.println(e.getMessage());
            throw  new CustormException(CodeAndMsg.ERRORUPLOAD);
        }
        userService.savHeadPortrait(fileName);
        return new JSONObject().fluentPut("code", CodeAndMsg.SUCESSUPLOAD.getCode())
                .fluentPut("msg", CodeAndMsg.SUCESSUPLOAD.getMsg())
                .fluentPut("fileName",fileName)
                .fluentPut("url", "image/" +fileName);

    }

    /**
     * 獲取驗證碼
     * @param phone
     * @return
     * @throws Exception
     */
    @PostMapping("/getCaptcha.do")
    @ApiOperation(value = "獲取手機驗證碼")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "電話號碼", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject getCaptcha(String phone)throws Exception{
        userService.getCaptcha(phone);
        return HANDLESUCESS.getJSON();
    }

    /**
     * 添加收货地址
     */
    @PostMapping("/addReceAddres.do")
    @ApiOperation(value = "添加收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recName", value = "收件人", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recPhone", value = "电话", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "placeId", value = "地区id(备注:与提货点id不能同时为空)",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pickUpStationId", value = "提货点id(备注:与地区id不能同时为空)",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recDetailedAddr", value = "详细地址", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recRemarks", value = "备注",  dataType = "String", paramType = "query"),
    })
    public JSONObject addReceAddres(String recName, String recPhone,String placeId,String pickUpStationId,String recDetailedAddr,String recRemarks) {
        receAddresService.add(recName,recPhone,placeId,pickUpStationId,recDetailedAddr,recRemarks,getUserId()+"");
        return HANDLESUCESS.getJSON();
    }


    /**
     * 修改收货地址
     */
    @PostMapping("/editReceAddres.do")
    @ApiOperation(value = "修改收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recId", value = "地址id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recName", value = "收件人", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recPhone", value = "电话", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "placeId", value = "地区id(备注:与提货点id不能同时为空)",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pickUpStationId", value = "提货点id(备注:与地区id不能同时为空)",  dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recDetailedAddr", value = "详细地址", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "recRemarks", value = "备注",  dataType = "String", paramType = "query"),
    })
    public JSONObject editReceAddres(String recId, String recName, String recPhone,String placeId,String pickUpStationId,String recDetailedAddr,String recRemarks)throws Exception {
        receAddresService.edit(new ReceAddres(recId,recName,recPhone,placeId,pickUpStationId,recDetailedAddr,recRemarks,getUserId()+""));
        return EDITSUCESS.getJSON();
    }

    /**
     * 修改收货地址
     */
    @PostMapping("/deleteReceAddres.do")
    @ApiOperation(value = "刪除收货地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recId", value = "地址id", required = true, dataType = "String", paramType = "query"),
    })

    public JSONObject deleteReceAddres(String recId) throws Exception {
        receAddresService.delete(recId,getUserId()+"");
        return HANDLESUCESS.getJSON();
    }
    /**
     * 保存appId
     */
    @PostMapping("/updateAppId.do")
    @ApiOperation(value = "保存appId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", value = "極光集成註冊id", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject updateAppId(String appId) throws Exception {
        userService.updateAppId(appId);
        return HANDLESUCESS.getJSON();
    }

    /**
     * 查询收货地址信息
     */
    @ApiOperation(value = "获取当前用户收货地址")
    @PostMapping("/getAllReceAddres.do")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recMode", value = "地址类型(1.自提,2.送货上门.null.所有)", required = false, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "limit", value = "条数", required = true, dataType = "int", paramType = "query"),
    })
    public JSONObject getAllReceAddres(Integer recMode,Integer page,Integer limit) {
        return SUCESS.getJSON(receAddresService.listPage(recMode,getUserId()+"", PageRequest.of(page-1,limit, Sort.by(Sort.Direction.DESC,"recUpdateTime"))));
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String", paramType = "query"),
    })
    public JSONObject doLogin(String username, String password,HttpServletRequest request) {
        if (StringUtil.isBlank(username, password)) {
            return CodeAndMsg.PARAM_ERROR.getJSON();
        }
        try {
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
            SecurityUtils.getSubject().login(usernamePasswordToken);
            User user = getUser();
            user.setUserType(user.getType());
            if(user.getUserType().equals(ProjectConfig.UserType.STAFF.getValue())){
                user.setUserType(staffService.getUserType(user.getUserId()));
            }
            addLoginRecord(user.getUserId(), request);
            return USER_LOGIN_SUCESS.getJSON(user).fluentPut(ShiroSessionManager.getAUTHORIZATION(),SecurityUtils.getSubject().getSession().getId());
        } catch (IncorrectCredentialsException ice) {
            return CodeAndMsg.USER_PASSWORDERROR.getJSON();
        } catch (UnknownAccountException uae) {
            return CodeAndMsg.USER_NOTFINDUSERNAME.getJSON();
        } catch (LockedAccountException e) {
            return CodeAndMsg.USER_BANNED.getJSON();
        } catch (ExcessiveAttemptsException eae) {
            return CodeAndMsg.ERROR.getJSON();
        }
    }
    @RequiresPermissions("member:addTokenRecord")
    @ApiOperation(value = "用户充值")
    @PostMapping("/addTokenRecord")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rechargeAmount", value = "充值數量", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "rechargeMode", value = "充值方式(1:轉賬,2:現金)", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用戶id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "rechargeComments", value = "描述", required = false, dataType = "String", paramType = "query"),
    })
    public JSONObject addRecord(@RequestParam("rechargeAmount") Integer rechargeAmount,
                                @RequestParam("rechargeMode") Integer rechargeMode,
                                @RequestParam("userId") String userId,
                                @RequestParam(value = "rechargeComments",required = false)String rechargeComments) {
        RechargeRecord rechargeRecord = new RechargeRecord(Double.valueOf(rechargeAmount),Integer.valueOf(userId),getUserId(),rechargeMode,rechargeComments);
        rechargeRecordService.add(rechargeRecord);
        return SUCESS.getJSON();
    }
    /**
     * 退出登錄
     */
    @PostMapping("/logout.do")
    @ApiOperation(value = "退出登錄")
    public JSONObject logout() throws Exception {
        loginOut();
        return HANDLESUCESS.getJSON();
    }
//    /**
//     * 领取包裹
//     **/
//    @RequiresPermissions("user:edit")
//    @ResponseBody
//    @RequestMapping("/pickUpParts.do")
//    public JsonResult pickUpParts(Integer userId) {
//        User byId = userService.getById(userId);
//        if (userService.updatePsw(userId, byId.getUsername(), "123456")) {
//            return JsonResult.ok("重置成功");
//        } else {
//            return JsonResult.error("重置失败");
//        }
//    }


    /**
     * 添加登录日志
     */
    private void addLoginRecord(Integer userId, HttpServletRequest request) {
        UserAgentGetter agentGetter = new UserAgentGetter(request);
        // 添加到登录日志
        LoginRecord loginRecord = new LoginRecord();
        loginRecord.setUserId(userId);
        loginRecord.setOsName(agentGetter.getOS());
        loginRecord.setDevice(agentGetter.getDevice());
        loginRecord.setBrowserType(agentGetter.getBrowser());
        loginRecord.setIpAddress(agentGetter.getIpAddr());
        loginRecordService.add(loginRecord);
    }



}

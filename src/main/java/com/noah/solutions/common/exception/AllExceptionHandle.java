package com.noah.solutions.common.exception;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@ControllerAdvice
public class AllExceptionHandle {

    private static final Logger log = LoggerFactory.getLogger(AllExceptionHandle.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exception(Exception ex){
        JSONObject jsonObject = new JSONObject();
        if(ex instanceof CustormException){
            if (((CustormException) ex).getUrl()!=null)
                return exceptionHandle((CustormException) ex);
            jsonObject.put("code",((CustormException) ex).getCode());
            jsonObject.put("msg",ex.getMessage());
        }else if (ex instanceof UnauthorizedException) {
            jsonObject.put("code", 403);
            jsonObject.put("msg", "没有访问权限");
        }else{
            jsonObject.put("code", CodeAndMsg.ERROR.getCode());
            jsonObject.put("msg", CodeAndMsg.ERROR.getMsg());
        }
        ex.printStackTrace();
//        System.out.println(ex.getMessage());
        return jsonObject;
    }

    //返回页面
    public ModelAndView exceptionHandle(CustormException ex){
        // String msg="异常信息>>>>>异常名:"+ex.getClass()+"||方法名:"+ex.getStackTrace()[0].getMethodName()+"||类名:"+ex.getStackTrace()[0].getClassName()+"||行数:"+ex.getStackTrace()[0].getLineNumber();
        /* return MyResult.error(msg);*/
        /*System.out.println("调用了登录异常处理");*/
        ModelAndView modelAndView = new ModelAndView(ex.getUrl());
        modelAndView.getModelMap().addAttribute("code",ex.getCode());
        modelAndView.getModelMap().addAttribute("msg",ex.getMessage());
        saveErrorLog(ex);
        return modelAndView;
    }

    private void saveErrorLog(Exception ex){
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
//        ex.printStackTrace(new java.io.PrintWriter(buf, true));
        String  expMessage = buf.toString();
        try {
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //记录到日志
        log.error("AllExceptionHandle,捕获异常:"+ ex.getMessage() + ";eString:" + expMessage);
    }
}

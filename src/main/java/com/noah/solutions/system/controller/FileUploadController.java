package com.noah.solutions.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.exception.CodeAndMsg;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 */
@RestController
@RequestMapping("/file")
public class FileUploadController {
    /*
     * 图片命名格式
     */
    private static final String DEFAULT_SUB_FOLDER_FORMAT_AUTO = "yyyyMMddHHmmss";


    /*
     * 上传图片文件夹
     */
//    /var/lib/tomcat8/webapps/order_Image

    /*
     * 上传单张图片
     */
    @RequestMapping(value = "/uploadImages")
    public JSONObject uplodaImg(@RequestParam(value = "uploadFile",required = false) MultipartFile uploadFile,
                          HttpServletRequest request)
    {
        try {
            if (uploadFile == null) {
                uploadFile = requestMultipartFile(request).get(0);
            }
            String fileName = uploadFile.getOriginalFilename();
            if(fileName.contains("/")){
                fileName = fileName.substring(fileName.lastIndexOf("/"));
            }
            fileName =  transferTo(uploadFile,fileName);
            return new JSONObject().fluentPut("code", CodeAndMsg.SUCESSUPLOAD.getCode())
                    .fluentPut("msg", CodeAndMsg.SUCESSUPLOAD.getMsg())
                    .fluentPut("fileName",fileName)
                    .fluentPut("url", "../file/fileDown?fileName=" +fileName);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject().fluentPut("code", CodeAndMsg.ERRORUPLOAD.getCode())
                .fluentPut("msg", CodeAndMsg.ERRORUPLOAD.getMsg());
    }

    /*
     * 上传图片
     */
    @RequestMapping(value = "/uploadAllImages")
    public JSONObject uploadAllImages(@RequestParam(value = "files",required = false) MultipartFile[] files,@RequestParam(value = "type") Integer type, HttpServletRequest request)throws Exception {
            if (files == null) {
                List<MultipartFile> list = requestMultipartFile(request);
                files = new MultipartFile[list.size()];
                list.toArray(files);
            }
            List<String> oldFileNames=new ArrayList<>();
            List<String> path=new ArrayList<>();
            List<String> fileNames=new ArrayList<>();
            for (MultipartFile file1:files){
                String fileName = file1.getOriginalFilename();
                oldFileNames.add(fileName);
                transferTo(file1,fileName);
                File fileThumbnail = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY +"thumbnail-"+fileName);

                path.add(fileName);
                fileNames.add(fileName);

            }
        return new JSONObject().fluentPut("code", CodeAndMsg.SUCESSUPLOAD.getCode())
                .fluentPut("msg", CodeAndMsg.SUCESSUPLOAD.getMsg())
                .fluentPut("oldFileNames", oldFileNames)
                .fluentPut("fileNames", fileNames)
                .fluentPut("path", path);
    }


    private String  transferTo(MultipartFile file1,String fileName)throws Exception{
        DateFormat df = new SimpleDateFormat(DEFAULT_SUB_FOLDER_FORMAT_AUTO);
        fileName = df.format(new Date()) +"_@"+fileName.replace("&"," ");
        File file = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY , fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        file1.transferTo(file);

        return fileName;
    }

    private List<MultipartFile> requestMultipartFile(HttpServletRequest request) throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        request.setCharacterEncoding("UTF-8");
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //** 页面控件的文件流* *//*
        List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
        Map map =multipartRequest.getFileMap();
        for (Iterator i = map.keySet().iterator(); i.hasNext();) {
            Object obj = i.next();
            multipartFiles.add((MultipartFile) map.get(obj));
        }
        return multipartFiles;
    }


}

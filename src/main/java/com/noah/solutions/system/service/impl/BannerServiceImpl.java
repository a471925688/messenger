package com.noah.solutions.system.service.impl;

import com.noah.solutions.common.ProjectConfig;
import com.noah.solutions.common.utils.BeanUtils;
import com.noah.solutions.system.entity.Banner;
import com.noah.solutions.system.repository.BannerRepository;
import com.noah.solutions.system.service.BannerService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Resource
    private BannerRepository bannerRepository;



    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  start//////////////////////////////////////////////////////////
    @Override
    public Page list(Integer size, Integer current, Banner banner) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("user.username", ExampleMatcher.GenericPropertyMatchers.contains());
        return bannerRepository.findAll(Example.of(banner,matcher), PageRequest.of(size-1,current));
    }

    @Override
    public List<Banner> getImageBanner() {
        return bannerRepository.findAllByTypeId("1");
    }

    @Override
    public List<Banner> getInfoBanner() {
        return bannerRepository.findAllByTypeId("2");
    }


    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////
    //////////////////////////////查询部分  end//////////////////////////////////////////////////////////








    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  start//////////////////////////////////////////////////////////


    @Override
    @Transactional
    public void add(Banner banner)throws Exception {
        fileHandle(banner.getImageName());
        bannerRepository.save(banner);
    }


    @Override
    @Transactional
    public void update(Banner banner)throws Exception {
        Banner oldBanner = bannerRepository.getOne(banner.getId());
        if(!banner.getImageName().equals(oldBanner.getImageName())){
            fileHandle(banner.getImageName());
            new File(ProjectConfig.FILE_IMAGE_DIRECTORY+oldBanner.getImageName()).delete();
        }
        BeanUtils.copyNotNullProperties(banner,oldBanner);
        bannerRepository.save(oldBanner);
    }

    @Override
    @Transactional
    public void del(String bannerId) {
        bannerRepository.deleteById(bannerId);
    }

    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////
    //////////////////////////////增删改部分  end//////////////////////////////////////////////////////////








    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  start//////////////////////////////////////////////////////////
    private  void fileHandle(String name) throws IOException {
        if(StringUtils.isEmpty(name))
            return;
        File file = new File(ProjectConfig.FILE_TEMPORARY_DIRECTORY+name);
        if(file.exists()){
            FileUtil.copyFile(file,new File(ProjectConfig.FILE_IMAGE_DIRECTORY+name));
            file.delete();
        }
    }
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////
    //////////////////////////////逻辑处理部分  end//////////////////////////////////////////////////////////

}


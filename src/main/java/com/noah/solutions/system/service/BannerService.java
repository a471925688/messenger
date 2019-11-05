package com.noah.solutions.system.service;

import com.noah.solutions.system.entity.Banner;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BannerService {
    Page list(Integer page, Integer limit, Banner banner);

    void add(Banner banner)throws Exception;

    void update(Banner banner)throws Exception;

    List<Banner> getImageBanner();

    List<Banner> getInfoBanner();

    void del(String bannerId);
}

package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.Banner;
import com.noah.solutions.system.entity.BannerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerRepository extends JpaRepository<Banner,String>{

    List<Banner> findAllByTypeId(String typeId);
}

package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.BannerType;
import com.noah.solutions.system.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerTypeRepository extends JpaRepository<BannerType,String>{


}

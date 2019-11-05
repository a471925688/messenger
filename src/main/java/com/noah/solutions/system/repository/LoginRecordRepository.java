package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.LoginRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecord,Integer>, JpaSpecificationExecutor<LoginRecord> {

//    List<LoginRecord> listFull(Page<LoginRecord> page, @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("account") String account);

    @Query(value = "select lr from LoginRecord lr where lr.createTime>?1 and lr.createTime<=?2 and lr.username like CONCAT('%',?3,'%')",nativeQuery = true)
    Page listByTime(String startDate, String endDate, String username, Pageable pageable);


//    Page findPageByContent(Specification specification, Pageable of);
}

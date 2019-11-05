package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.Banner;
import com.noah.solutions.system.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label,String>{
    List<Label> findAllByStatus(Integer status);

    @Modifying
    @Query("update Label  lb set lb.status=?2 where lb.no=?1")
    void updateStatus(String no,Integer status);

    @Modifying
    @Query("update Label lb set lb.isDelete=1 where lb.no=?1")
    void delByNo(String no);


}

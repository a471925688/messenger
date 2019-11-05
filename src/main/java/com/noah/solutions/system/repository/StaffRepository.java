package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.Order;
import com.noah.solutions.system.entity.PickUpStation;
import com.noah.solutions.system.entity.Staff;
import com.noah.solutions.system.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff,Integer>, JpaSpecificationExecutor<Staff> {
    Staff findByUser_UserId(Integer userId);

    @Query("select s.pickUpStationId from Staff s where s.user.userId = ?1")
    String findStationIdByUserId(Integer userId);

    List<Staff> findAllByStaffTypeAndPickUpStationId(Integer staffType,String pickUpStationId);

    Staff findByStaffId(Integer staffId);

    @Query("select s.staffType from Staff s where s.userId=?1")
    Integer getType(Integer userId);


    @Query("select sf.user from Staff sf where sf.pickUpStationId = (select s.pickUpStationId from Staff  s where s.userId=?1) and sf.staffType=?2")
    List<User> selectByUserId(Integer userId,Integer staffType);

}

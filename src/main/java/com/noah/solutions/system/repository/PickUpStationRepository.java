package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.PickUpStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PickUpStationRepository extends JpaRepository<PickUpStation,String> {
    @Query("select p.pickUpStationName from PickUpStation p where p.pickUpStationId=?1")
    String findNameById(String pickUpStationId);

    @Modifying
    @Query("update PickUpStation ps set ps.isDelete = 1 where ps.pickUpStationId = ?1")
    void deleteById(String id);

}

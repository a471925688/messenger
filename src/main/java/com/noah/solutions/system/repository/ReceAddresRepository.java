package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.ReceAddres;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceAddresRepository extends JpaRepository<ReceAddres,String> {
    List<ReceAddres> getAllByUserId(String userId, Pageable pageable);
    List<ReceAddres> getAllByUserIdAndRecMode(String userId,Integer recMode, Pageable pageable);
    boolean existsByRecId(String recId);

    ReceAddres findByRecId(String recId);

    ReceAddres findByRecIdAndUserId(String recId,String userId);

    @Modifying
    @Query("update ReceAddres  ra set ra.isDelete = 1 where ra.recId=?1 and ra.userId=?2")
    void delete(String recId,String userId);

}

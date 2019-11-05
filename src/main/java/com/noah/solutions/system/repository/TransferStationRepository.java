package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.Staff;
import com.noah.solutions.system.entity.TransferStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferStationRepository extends JpaRepository<TransferStation,Integer> {

}

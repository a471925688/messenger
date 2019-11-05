package com.noah.solutions.system.repository;

import com.noah.solutions.system.entity.RechargeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RechargeRecordRepository extends JpaRepository<RechargeRecord,String>, JpaSpecificationExecutor<RechargeRecord> {

}

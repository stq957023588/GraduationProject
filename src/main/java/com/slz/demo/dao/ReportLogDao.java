package com.slz.demo.dao;

import com.slz.demo.pojo.ReportLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportLogDao extends JpaRepository<ReportLog,String >{

    List<ReportLog> findByUserIdAndStrategyId(Integer userId,String strategyId);

    List<ReportLog> findByStrategyId(String strategyId);

}

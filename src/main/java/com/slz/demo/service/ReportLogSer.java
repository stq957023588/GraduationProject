package com.slz.demo.service;

import com.slz.demo.pojo.ReportLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ReportLogSer {

    void save(ReportLog reportLog);

    void delete(ReportLog reportLog);

    Page<ReportLog> findAll(PageRequest pageRequest);

    List<ReportLog> findByUserIdAndStrategyId(Integer userId,String strategyId);

    List<ReportLog> findByStrategyId(String strategyId);
}

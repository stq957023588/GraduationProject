package com.slz.demo.service;

import com.slz.demo.dao.ReportLogDao;
import com.slz.demo.pojo.ReportLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportLogSerImp implements ReportLogSer{
    @Autowired
    ReportLogDao reportLogDao;

    @Override
    public void save(ReportLog reportLog) {
        reportLogDao.save(reportLog);
    }

    @Override
    public void delete(ReportLog reportLog) {
        reportLogDao.delete(reportLog);
    }

    @Override
    public Page<ReportLog> findAll(PageRequest pageRequest) {
        return reportLogDao.findAll(pageRequest);
    }

    @Override
    public List<ReportLog> findByUserIdAndStrategyId(Integer userId, String strategyId) {
        return reportLogDao.findByUserIdAndStrategyId(userId,strategyId);
    }

    @Override
    public List<ReportLog> findByStrategyId(String strategyId) {
        return reportLogDao.findByStrategyId(strategyId);
    }
}

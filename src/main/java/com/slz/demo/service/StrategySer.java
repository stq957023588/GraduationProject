package com.slz.demo.service;

import com.slz.demo.pojo.Strategy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.sql.Timestamp;
import java.util.List;

public interface StrategySer {
    void save(Strategy strategy);
    List<Strategy> findAll();
    void delete(Strategy strategy);
    Page<Strategy> findAll(PageRequest pageRequest);
    Strategy findById(String id);
    List<Strategy> showStrategiesByOperation(Integer start, Integer end, Timestamp startTime, Timestamp endTime,String operationId);
    List<Strategy> findByAccount(String account);
    List<Strategy> findByCityId(int page,int size,List<String > idList);
    List<Strategy> findOrderByTime(int page,int size);
    List<Strategy> findByTitleLikeOrAttractionsNameLike(String condition);
    List<Strategy> findByIdList(List<String > idList,PageRequest pageRequest);
    int countByAccount(String account);
    List<Strategy> findByIsLegal(Integer isLegal);
}

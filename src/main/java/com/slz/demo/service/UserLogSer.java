package com.slz.demo.service;

import com.slz.demo.pojo.UserLog;

import java.util.List;
import java.util.Map;

public interface UserLogSer {

    void save(UserLog userLog);

    List<UserLog> findAll();

    void delete(UserLog userLog);

    UserLog findByUserIdAndStrategyIdAndAndOperationId(Integer userId,String strategyId,String operationId);

    UserLog findByIpAndAndStrategyIdAndAndOperationId(String ip,String strategyId,String operationId );

    int getNumByParam(Map<String ,String > param);

    List<UserLog> findByUserIdAndOperationIdOrderByTime(Integer userId,String operationId);
}

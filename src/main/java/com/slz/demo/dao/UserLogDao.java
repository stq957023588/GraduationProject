package com.slz.demo.dao;

import com.slz.demo.pojo.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserLogDao extends JpaRepository<UserLog,String >,JpaSpecificationExecutor<UserLog>{

    UserLog findByUserIdAndStrategyIdAndAndOperationId(Integer userId,String strategyId,String operationId);

    UserLog findByIpAndAndStrategyIdAndAndOperationId(String ip,String strategyId,String operationId );

    List<UserLog> findByUserIdAndOperationIdOrderByTimeDesc(Integer userId,String operationId);

}

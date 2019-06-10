package com.slz.demo.service;

import com.slz.demo.dao.UserLogDao;
import com.slz.demo.pojo.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserLogSerImp implements UserLogSer {
    @Autowired
    UserLogDao userLogDao;

    @Override
    public void save(UserLog userLog) {
        userLogDao.save(userLog);
    }

    @Override
    public List<UserLog> findAll() {
        return userLogDao.findAll();
    }

    @Override
    public void delete(UserLog userLog) {
        userLogDao.delete(userLog);
    }

    @Override
    public UserLog findByUserIdAndStrategyIdAndAndOperationId(Integer userId, String strategyId, String operationId) {
        return userLogDao.findByUserIdAndStrategyIdAndAndOperationId(userId,strategyId,operationId);
    }

    @Override
    public UserLog findByIpAndAndStrategyIdAndAndOperationId(String ip, String strategyId, String operationId) {
        return userLogDao.findByIpAndAndStrategyIdAndAndOperationId(ip, strategyId, operationId);
    }

    @Override
    public int getNumByParam(Map<String, String> param) {
        Specification<UserLog> specification=new Specification<UserLog>() {
            @Override
            public Predicate toPredicate(Root<UserLog> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                Set<String > keySet=param.keySet();
                for (String  key:keySet){
                    list.add(criteriaBuilder.equal(root.get(key).as(String.class),param.get(key)));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };


        return (int)userLogDao.count(specification);
    }

    @Override
    public List<UserLog> findByUserIdAndOperationIdOrderByTime(Integer userId, String operationId) {
        return userLogDao.findByUserIdAndOperationIdOrderByTimeDesc(userId,operationId);
    }


}

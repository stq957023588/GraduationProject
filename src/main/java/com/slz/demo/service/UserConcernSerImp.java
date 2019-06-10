package com.slz.demo.service;

import com.slz.demo.dao.UserConcernDao;
import com.slz.demo.pojo.UserConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class UserConcernSerImp implements UserConcernSer{
    @Autowired
    UserConcernDao userConcernDao;


    @Override
    public void save(UserConcern userConcern) {
        userConcernDao.save(userConcern);
    }

    @Override
    public void delete(UserConcern userConcern) {
        userConcernDao.delete(userConcern);
    }

    @Override
    public List<UserConcern> findByUserId(Integer userId) {
        return userConcernDao.findByUserId(userId);
    }

    @Override
    public List<UserConcern> findByAuthorAccount(String account) {
        return userConcernDao.findByAuthorAccount(account);
    }

    @Override
    public int getFansNum(String account) {
        Specification<UserConcern> specification=new Specification<UserConcern>() {
            @Override
            public Predicate toPredicate(Root<UserConcern> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("authorAccount").as(String .class),account);
            }
        };
        return (int)userConcernDao.count(specification);
    }

    @Override
    public int getConcernedNum(Integer id) {
        Specification<UserConcern> specification=new Specification<UserConcern>() {
            @Override
            public Predicate toPredicate(Root<UserConcern> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("userId").as(Integer .class),id);
            }
        };
        return (int)userConcernDao.count(specification);
    }

    @Override
    public boolean findByAuthorAccountAndUserId(String account, Integer userId) {
        UserConcern userConcern=userConcernDao.findByAuthorAccountAndUserId(account,userId);
        if(userConcern==null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void deleteByAuthorAccountAndUserId(String account, Integer id) {
        userConcernDao.deleteByAuthorAccountAndUserId(account,id);
    }
}

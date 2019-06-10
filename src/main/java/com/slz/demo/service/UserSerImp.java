package com.slz.demo.service;

import com.slz.demo.dao.UserDao;
import com.slz.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
@Service
public class UserSerImp implements UserSer{
    @Autowired
    UserDao userDao;

    @Override
    public void sava(User user) {
        userDao.save(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Page<User> findAll(PageRequest pageRequest) {
        return userDao.findAll(pageRequest);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public User findByAccount(String account) {
        return userDao.findByAccount(account);
    }

    @Override
    public List<User> findByIdList(int page, int size, List<Integer> idList) {
        if(idList.size()<=0){
            return new ArrayList<>();
        }
        PageRequest pageRequest=PageRequest.of(page,size);
        Specification<User> specification=new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(criteriaBuilder.in(root.get("id")).value(idList));
            }
        };
        return userDao.findAll(specification,pageRequest).getContent();
    }

    @Override
    public List<User> findByAccountList(int page, int size, List<String> accountList) {
        PageRequest pageRequest=PageRequest.of(page,size);
        Specification<User> specification=new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate=criteriaBuilder.and(criteriaBuilder.in(root.get("account")).value(accountList));
                return predicate;
            }
        };
        return userDao.findAll(specification,pageRequest).getContent();
    }

    @Override
    public List<User> findByNameLikeOrAccount(String condition) {
        return userDao.findByNameLikeOrAccount("%"+condition+"%",condition);
    }
}

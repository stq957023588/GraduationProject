package com.slz.demo.service;

import com.slz.demo.dao.CommentDao;
import com.slz.demo.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CommentSerImp implements CommentSer {
    @Autowired
    CommentDao commentDao;


    @Override
    public void save(Comment comment) {
        commentDao.save(comment);
    }

    @Override
    public void delete(String  id) {
        commentDao.deleteById(id );
    }

    @Override
    public List<Comment> findAll() {
        return commentDao.findAll();
    }

    @Override
    public List<Comment> findByStrategyIdOrderByTime(String strategyId) {
        return commentDao.findByStrategyIdOrderByTime(strategyId);
    }

    @Override
    public List<Comment> findByUserAccountOrderByTimeDesc(String account) {
        return commentDao.findByUserAccountOrderByTimeDesc(account);
    }

    @Override
    public List<Comment> findByStrategyIdOrderByTime(PageRequest pageRequest, String strategyId) {
        return commentDao.findAll(getSpecification("strategyId",strategyId),pageRequest).getContent();
    }

    @Override
    public List<Comment> findByUserAccountOrderByTimeDesc(PageRequest pageRequest, String account) {
        return commentDao.findAll(getSpecification("userAccount",account),pageRequest).getContent();
    }

    @Override
    public long countStrategyIdOrderByTime(String strategyId) {
        return commentDao.count(getSpecification("strategyId",strategyId));
    }

    @Override
    public long countUserAccountOrderByTime(String account) {
        return commentDao.count(getSpecification("userAccount",account));
    }


    private Specification<Comment> getSpecification(String rootStr,String value){
        Specification<Comment> specification=new Specification<Comment>() {
            @Override
            public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.equal(root.get(rootStr).as(String .class),value));
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("time")));

                return criteriaQuery.getRestriction();
            }
        };
        return specification;
    }
}

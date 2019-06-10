package com.slz.demo.service;

import com.slz.demo.dao.CityDao;
import com.slz.demo.dao.StrategyDao;
import com.slz.demo.enums.OperationEnum;
import com.slz.demo.pojo.Strategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class StrategySerImp implements StrategySer {
    @Autowired
    StrategyDao strategiesDao;

    @Autowired
    CityDao cityDao;

    @Override
    public void save(Strategy strategy) {
        strategiesDao.save(strategy);
    }

    @Override
    public List<Strategy> findAll() {
        return strategiesDao.findAll();
    }

    @Override
    public void delete(Strategy strategy) {
        strategiesDao.delete(strategy);
    }

    @Override
    public Page<Strategy> findAll(PageRequest pageRequest) {
        return strategiesDao.findAll(pageRequest);
    }

    @Override
    public Strategy findById(String id) {
        return strategiesDao.findById(id).orElse(null);
    }

    @Override
    public List<Strategy> showStrategiesByOperation(Integer start, Integer end, Timestamp startTime, Timestamp endTime,String operationId) {
        return strategiesDao.showStrategiesByOperation(start,end,startTime,endTime,operationId);
    }

    @Override
    public List<Strategy> findByAccount(String account) {
        return strategiesDao.findByAccountOrderByPublishTimeDesc(account);
    }

    @Override
    public List<Strategy> findByCityId(int page,int size,List<String > idList){
        PageRequest pageRequest=PageRequest.of(page,size);
        Specification<Strategy> specification=new Specification<Strategy>() {
            @Override
            public Predicate toPredicate(Root<Strategy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                list.add(criteriaBuilder.and(criteriaBuilder.in(root.get("cityId")).value(idList)));
                list.add(criteriaBuilder.and(criteriaBuilder.in(root.get("isLegal")).value(Arrays.asList(1,0))));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return strategiesDao.findAll(specification,pageRequest).getContent();
    }

    public List<Strategy> findByCityId(List<String > cityIdList){
        Specification<Strategy> specification=new Specification<Strategy>() {
            @Override
            public Predicate toPredicate(Root<Strategy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                list.add(criteriaBuilder.and(criteriaBuilder.in(root.get("cityId")).value(cityIdList)));
                list.add(criteriaBuilder.and(criteriaBuilder.notEqual(root.get("isLegal").as(Integer .class),-1)));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return strategiesDao.findAll(specification);
    }


    @Override
    public List<Strategy> findOrderByTime(int page,int size){
        PageRequest pageRequest=PageRequest.of(page,size);
        Specification<Strategy> specification=new Specification<Strategy>() {
            @Override
            public Predicate toPredicate(Root<Strategy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                criteriaQuery.where(criteriaBuilder.notEqual(root.get("isLegal").as(Integer .class),-1));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("publishTime")));
                return criteriaQuery.getRestriction();
            }
        };
        return strategiesDao.findAll(specification,pageRequest).getContent();
    }

    @Override
    public List<Strategy> findByTitleLikeOrAttractionsNameLike(String condition) {
        return strategiesDao.findByIsLegalIsNotAndTitleLikeOrAttractionsNameLike(-1,condition,condition);
    }

    @Override
    public List<Strategy> findByIdList(List<String> idList,PageRequest pageRequest) {
        Specification<Strategy> specification=new Specification<Strategy>() {
            @Override
            public Predicate toPredicate(Root<Strategy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                list.add(criteriaBuilder.notEqual(root.get("isLegal").as(Integer .class),-1));
                list.add(criteriaBuilder.in(root.get("id")).value(idList));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return strategiesDao.findAll(specification,pageRequest).getContent();
    }

    @Override
    public int countByAccount(String account) {
        Specification<Strategy> specification=new Specification<Strategy>() {
            @Override
            public Predicate toPredicate(Root<Strategy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                list.add(criteriaBuilder.notEqual(root.get("isLegal").as(Integer .class),-1));
                list.add(criteriaBuilder.equal(root.get("account").as(String .class),account));

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return (int)strategiesDao.count(specification);
    }

    @Override
    public List<Strategy> findByIsLegal(Integer isLegal) {
        return strategiesDao.findByIsLegal(isLegal);
    }


    public List<Strategy> findByAccount(int page,int size,List<String > accountList){
        PageRequest pageRequest=PageRequest.of(page,size);
        Specification<Strategy> specification=new Specification<Strategy>() {
            @Override
            public Predicate toPredicate(Root<Strategy> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                list.add(criteriaBuilder.and(criteriaBuilder.in(root.get("account")).value(accountList)));
                list.add(criteriaBuilder.notEqual(root.get("isLegal").as(Integer .class),-1));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("publishTime")));
                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };

        return strategiesDao.findAll(specification,pageRequest).getContent();
    }
}

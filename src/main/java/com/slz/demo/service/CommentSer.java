package com.slz.demo.service;

import com.slz.demo.pojo.Comment;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CommentSer {

    void save(Comment comment);

    void delete(String  id);

    List<Comment> findAll();

    List<Comment> findByStrategyIdOrderByTime(String strategyId);

    List<Comment> findByStrategyIdOrderByTime(PageRequest pageRequest,String strategyId);

    List<Comment> findByUserAccountOrderByTimeDesc(String  account);

    List<Comment> findByUserAccountOrderByTimeDesc(PageRequest pageRequest,String  account);

    long countStrategyIdOrderByTime(String strategyId);

    long countUserAccountOrderByTime(String account);
}

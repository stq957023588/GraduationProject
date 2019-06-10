package com.slz.demo.dao;

import com.slz.demo.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,String >,JpaSpecificationExecutor<Comment>{

    List<Comment> findByStrategyIdOrderByTime(String strategyId);

    List<Comment> findByUserAccountOrderByTimeDesc(String  account);

}

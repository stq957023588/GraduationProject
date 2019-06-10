package com.slz.demo.dao;

import com.slz.demo.pojo.UserConcern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserConcernDao extends JpaRepository<UserConcern,String >,JpaSpecificationExecutor<UserConcern>{

    List<UserConcern> findByUserId(Integer userId);

    List<UserConcern> findByAuthorAccount(String account);

    UserConcern findByAuthorAccountAndUserId(String account,Integer userId);

    @Transactional
    void deleteByAuthorAccountAndUserId(String account,Integer id);


}

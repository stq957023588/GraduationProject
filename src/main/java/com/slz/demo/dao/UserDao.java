package com.slz.demo.dao;

import com.slz.demo.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserDao extends JpaRepository<User,Integer>,JpaSpecificationExecutor<User>{
    User findByAccount(String account);

    List<User> findByNameLikeOrAccount(String name,String account);
}

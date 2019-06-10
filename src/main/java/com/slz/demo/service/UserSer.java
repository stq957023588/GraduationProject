package com.slz.demo.service;

import com.slz.demo.pojo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserSer {

    void sava(User user);

    void delete(User user);

    List<User> findAll();

    Page<User> findAll(PageRequest pageRequest);

    User findById(Integer id);

    User findByAccount(String account);

    List<User> findByIdList(int page,int size,List<Integer> idList);

    List<User> findByAccountList(int page,int size,List<String > accountList);

    List<User> findByNameLikeOrAccount(String condition);

}

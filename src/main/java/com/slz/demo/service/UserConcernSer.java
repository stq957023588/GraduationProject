package com.slz.demo.service;

import com.slz.demo.pojo.UserConcern;

import java.util.List;

public interface UserConcernSer {


    void save(UserConcern userConcern);

    void delete(UserConcern userConcern);

    List<UserConcern> findByUserId(Integer userId);

    List<UserConcern> findByAuthorAccount(String account);

    int getFansNum(String  account);

    int getConcernedNum(Integer id);

    boolean findByAuthorAccountAndUserId(String account,Integer userId);

    void deleteByAuthorAccountAndUserId(String account,Integer id);
}

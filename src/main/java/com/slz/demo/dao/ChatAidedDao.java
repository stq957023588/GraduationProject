package com.slz.demo.dao;

import com.slz.demo.pojo.ChatAided;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ChatAidedDao extends JpaRepository<ChatAided,String > {



    List<ChatAided> findByAccount1AndIsShowOrAccount2(String account1,Integer isShow,String account2);



    @Transactional
    @Query(value = "select *\n" +
            "from CHAT_AIDED\n" +
            "where ((ACCOUNT1=(?1) and ACCOUNT2=(?2)) or (ACCOUNT1=(?2) and ACCOUNT2=(?1)))",
            nativeQuery = true)
    ChatAided checkByChatMembersAccount(String Account1,String Account);



}

package com.slz.demo.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChatAidedDaoTest {

    @Autowired
    ChatAidedDao chatAidedDao;


    @Test
    public void findByAccount1AndIsShowOrAccount2() {


    }


    @Test
    public void checkByChatMembersAccount() {
        
        System.out.println(chatAidedDao.checkByChatMembersAccount("44","456"));
        
        
    }
}
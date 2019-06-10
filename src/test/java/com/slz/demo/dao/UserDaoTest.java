package com.slz.demo.dao;

import com.slz.demo.pojo.User;
import com.slz.demo.utils.Tools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired
    UserDao userDao;

    @Test
    public void insert(){
        User user=new User();
        user.setAccount("456");
        user.setPassword("123");
        user.setName("ymc");
        userDao.save(user);
    }

    @Test
    public void tttt(){
        System.out.println(userDao.findByNameLikeOrAccount("%ss%","6666"));
        Tools.getListContent(userDao.findByNameLikeOrAccount("%ss%","6666"));
    }
}
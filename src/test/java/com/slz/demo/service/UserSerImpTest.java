package com.slz.demo.service;

import com.slz.demo.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSerImpTest {
    @Autowired
    UserSerImp userSer;
    @Test
    public void sava() {
        User user=new User();
        user.setId(9);
        user.setAccount("456");
        user.setPassword("123");
        user.setName("ymc");
        System.out.println(user);
        userSer.sava(user);
    }

    @Test
    public void delete() {
        userSer.delete(getUsers(4));
    }

    @Test
    public void findAll() {
        List<User> list=userSer.findAll();
        for (User u :
                list) {
            System.out.println(u);
        }
    }

    @Test
    public void findAll1() {
        PageRequest request=PageRequest.of(0,2);
        List<User> list=userSer.findAll(request).getContent();
        for (User u :
                list) {
            System.out.println(u);
        }
    }

    @Test
    public void findById() {
        User user =userSer.findById(2);
        System.out.println(user);
    }

    @Test
    public void findByAccount() {
        User user =userSer.findByAccount("ss");
        System.out.println(user);
    }

    public User getUsers(Integer id){
        User user=new User();
        user.setId(id);
        user.setAccount("456");
        user.setPassword("123");
        user.setName("ymc");
        return user;
    }
}
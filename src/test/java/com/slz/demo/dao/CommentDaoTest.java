package com.slz.demo.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentDaoTest {

    @Autowired
    CommentDao dao;

    @Test
    public void findByStrategyIdOrderByTimeDesc() {
        dao.findByStrategyIdOrderByTime("S_99f9bce1-b7f6-4278-a30a-9209d8e520fc")
                .stream()
                .forEach(e->System.out.println(e));
    }

    @Test
    public void findByUserAccountOrderByTimeDesc() {
        dao.findByUserAccountOrderByTimeDesc("123")
                .stream()
                .forEach(e->System.out.println(e));
    }
}
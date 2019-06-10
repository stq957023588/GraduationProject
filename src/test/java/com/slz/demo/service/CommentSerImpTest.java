package com.slz.demo.service;

import com.slz.demo.pojo.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentSerImpTest {

    @Autowired
    CommentSerImp commentSerImp;

    @Test
    public void findByStrategyIdOrderByTime() {
        List<Comment> list=commentSerImp.
                findByStrategyIdOrderByTime(PageRequest.of(1,3),"S_99f9bce1-b7f6-4278-a30a-9209d8e520fc");
        System.out.println(list.size());
        list.stream().forEach(e->System.out.println(e));


    }

    @Test
    public void findByUserAccountOrderByTimeDesc() {
        commentSerImp
                .findByUserAccountOrderByTimeDesc(PageRequest.of(1,3),"33")
                .stream()
                .forEach(e->System.out.println(e));
    }
}
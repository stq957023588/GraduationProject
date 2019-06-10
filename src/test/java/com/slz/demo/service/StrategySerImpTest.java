package com.slz.demo.service;

import com.slz.demo.pojo.Strategy;
import com.slz.demo.utils.Tools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategySerImpTest {
    @Autowired
    StrategySerImp imp;


    @Test
    public void save() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void findAll1() {
    }

    @Test
    public void findById() {
    }

    @Test
    public void findByAttractions_continent() {

//        Tools.getListContent(imp.findByAttractions_continent(0,1,"Asia"));
    }

    @Test
    public void findByAttractionsId(){
        List<String > idList= new ArrayList<>();
        idList.add("1");
        idList.add("2");
        List<Strategy> list=imp.findByCityId(0,1,idList);
        System.out.println(list.size());
        Tools.getListContent(list);
    }

}
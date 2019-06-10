package com.slz.demo.dao;

import com.slz.demo.pojo.Strategy;
import com.slz.demo.utils.Tools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyDaoTest {
    @Autowired
    StrategyDao dao;

    @Test
    public void save(){
        Strategy strategy =new Strategy();
        strategy.setId("yzzj_s");
//        strategy.setAttractions_id("yzzj0003");
//        strategy.setUserId(23);
        strategy.setContent("ymcdllx");
        strategy.setPublishTime(new Timestamp(System.currentTimeMillis()));
        dao.save(strategy);
    }

    @Test
    public void    findByTitleLikeOrAttractionsNameLike(){
        Tools.getListContent(dao.findByTitleLikeOrAttractionsNameLike("%木%","木"));

    }

    @Test
    public void findByTitleOrBeReportedAndIsLegalIsNot(){
        List<Strategy> list=dao.findByIsLegalIsNotAndTitleLikeOrAttractionsNameLike(-1,"test","乌镇");
        Tools.getListContent(list);
    }


}
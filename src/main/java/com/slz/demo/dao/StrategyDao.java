package com.slz.demo.dao;

import com.slz.demo.pojo.Strategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface StrategyDao extends JpaRepository<Strategy,String >,JpaSpecificationExecutor<Strategy> {
    List<Strategy> findByIsLegalIsNotAndTitleLikeOrAttractionsNameLike(int isLegal,String title,String an);

    List<Strategy> findByTitleLikeOrAttractionsNameLike(String condition1,String condition2);

    List<Strategy> findByAccountOrderByPublishTimeDesc(String account);

    List<Strategy> findByIsLegal(Integer isLegal);


    @Transactional
    @Modifying
    @Query(value = "select s.*,a.*\n" +
            "from strategies s\n" +
            "join attractions a\n" +
            "on s.attractions_id=a.id\n" +
            "where a.continent=(?1)", nativeQuery = true)
    List<Strategy> findByContinent(String continent);

    //g根据点赞数排序
    @Transactional
    @Modifying
    @Query(value = "select *\n" +
            "from(\n" +
            "select s.*,rownum r\n" +
            "from STRATEGIES s\n" +
            "join (select STRATEGY_ID\n" +
            "        from USER_LOG \n" +
            "        where OPERATION_ID=?5\n" +
            "        group by STRATEGY_ID\n" +
            "        order by count(*) DESC) t\n" +
            "on s.id=t.STRATEGY_ID\n" +
            "where s.publish_time between ?3 and ?4\n" +
            "and s.IS_LEGAL != -1)\n" +
            "where r between ?1 and ?2",
                nativeQuery = true)
    List<Strategy> showStrategiesByOperation(Integer start, Integer end, Timestamp startTime,Timestamp endTime,String operationId);



}

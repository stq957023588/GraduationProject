package com.slz.demo.VO;

import com.slz.demo.pojo.Strategy;
import com.slz.demo.service.CitySerImp;
import com.slz.demo.service.StrategySerImp;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Data
public class StrategyMsg{
    private String cityName;
    private String  publishTimeStr;
    private Strategy strategy;
    private Integer watchNum;
    private Integer collectNum;
    private Integer upgoodNum;

    public StrategyMsg(Strategy strategy,String cityName){
        this.cityName=cityName;
        this.strategy=strategy;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.publishTimeStr=sdf.format(strategy.getPublishTime());
    }



}

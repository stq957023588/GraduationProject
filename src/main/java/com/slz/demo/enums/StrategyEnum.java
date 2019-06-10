package com.slz.demo.enums;

import lombok.Getter;

@Getter
public enum StrategyEnum {
    ID("userId","user_id"),
    AttractionsId("cityId","attractions_id"),
    ReadingVolume("readingVolume","reading_volume"),
    PublishTime("publishTime","publish_time"),
    StrategyIdPrefix("1","S_"),

    ;



    private String  code;

    private String message;

    StrategyEnum(String code,String message){
        this.code=code;
        this.message=message;
    }

}

package com.slz.demo.enums;

import lombok.Getter;

@Getter
public enum PrefixEnum {
    ReportLogIdPrefix("1","RL_"),
    StrategyIdPrefix("2","S_"),
    CommentIdPrefix("","SC_"),
    UserConcernIdPrefix("","UC_"),
    CityPrefix("","Ci_"),
    ChatIdPrefix("","C_"),
    ChatAidedIdPrefix("","CA_"),

    ;


    private String  code;

    private String message;

    PrefixEnum(String code,String message){
        this.code=code;
        this.message=message;
    }
}

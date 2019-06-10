package com.slz.demo.enums;

public enum SuffixEnum {
    json("d",".json"),
    ;

    private String  code;

    private String message;

    SuffixEnum(String code,String message){
        this.code=code;
        this.message=message;
    }
}

package com.slz.demo.enums;

import lombok.Getter;

@Getter
public enum OperationEnum {
    watch("user_1","watch"),
    collect("collect","user_2"),
    upgood("upgood","user_3"),
    ;


    private String  code;

    private String message;

    OperationEnum(String code,String message){
        this.code=code;
        this.message=message;
    }
}

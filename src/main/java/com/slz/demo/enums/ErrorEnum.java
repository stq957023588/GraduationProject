package com.slz.demo.enums;

import lombok.Getter;

@Getter
public enum ErrorEnum{

    error1("e_1","Session is failured,Please log in again."),
    error2("e_2","Please login in first")
    ;



    private String  code;

    private String message;

    ErrorEnum(String code,String message){
        this.code=code;
        this.message=message;
    }
}

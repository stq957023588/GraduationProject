package com.slz.demo.enums;

import lombok.Getter;

@Getter
public enum PathEnum {

    tempPathPrefix("tempPathPrefix","D:\\Work\\Service\\LocalWarehouse_temp\\"),
    jpg("jpg",".jpg"),
    ServerPathPrefix("server","/picOnServer?fileName="),
    LocalResourcesPath("","D:\\Work\\WorkSpace\\WorkSpace_IDEA\\demo\\src\\main\\resources\\"),
    JsonPath("","D:\\Work\\WorkSpace\\WorkSpace_IDEA\\demo\\src\\main\\resources\\static\\json\\"),
    ;


    private String  code;

    private String message;

    PathEnum(String code,String message){
        this.code=code;
        this.message=message;
    }
}

package com.slz.demo.enums;

import lombok.Getter;

@Getter
public enum PromptEnum {
    loginFirst("","Please login in first"),
    hvaeBeenConcerned("","已关注"),
    commentSuccess("","评论成功")
    ;



    private String  code;

    private String message;

    PromptEnum(String code,String message){
        this.code=code;
        this.message=message;
    }
}

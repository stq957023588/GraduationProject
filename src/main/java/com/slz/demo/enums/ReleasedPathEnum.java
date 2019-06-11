package com.slz.demo.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ReleasedPathEnum {
    home("","/home"),
    releasedPath002("","/favicon.ico"),
    showRecommendedStrategies("","/showRecommendedStrategies"),
    websocket("","/websocket"),
    loginEnter("","/loginEnter"),
    showStrategyInfo("","/showStrategyInfo"),
    css("","/css"),
    js("","/js"),
    img("","/img"),
    plus("","/plus"),
    json("","/json"),
    picOnServer("","/picOnServer"),
    registerEnter("","/registerEnter"),
    fonts("","/fonts"),
    headPicCheck("","/headPicCheck"),
    showHeadPicByAccount("","/showHeadPicByAccount"),
    showHeadPic("","/showHeadPic"),
    releasedPath019("","/loginEnter"),
    searchStrategies("","/searchStrategies"),
    SearchResult("","/SearchResult"),
    registerCtrl("","/registerCtrl"),
    ForgetPassword("","/ForgetPassword"),





    searchStrategies55("","/searchStrategies"),
    test("","/test"),
    FeedBack("","/FeedBack"),
//    searchStrategies55("","/searchStrategies"),
//    searchStrategies55("","/searchStrategies"),
//    searchStrategies55("","/searchStrategies"),
    wechatTest("","/wechatTest"),
    redisTest1("","/redisTest1"),

    //test
//    personalPage("",""),
    ;

    private String  code;

    private String message;

    ReleasedPathEnum(String code,String message){
        this.code=code;
        this.message=message;
    }

}

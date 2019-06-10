package com.slz.demo.controller;

import com.slz.demo.VO.Message;
import com.slz.demo.utils.Tools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

@RestController
//@ConfigurationProperties(prefix = "wechat.config")
public class WeChatCtrl {
    @Value("${wechat.config.AppID}")
    private String AppID;
    @Value("${wechat.config.AppSecret}")
    private String AppSecret;
    @Value("${wechat.config.accessTokenUrl}")
    private String accessTokenUrl;
    @Value("${wechat.config.apiTicketUrl}")
    private String apiTicketUrl;

    @RequestMapping("/wechatTest")
    public Message wechatTest(){
        Message message=new Message();
        //获取accessToken
        String  accessToken=Tools.doGet(accessTokenUrl.replace("APPID",AppID).replace("APPSECRET",AppSecret)).get("access_token");
        //获取ticket
        String  apiTicket=Tools.doGet( apiTicketUrl.replace("ACCESS_TOKEN", accessToken)).get("ticket");

        Map<String, String> ret = new HashMap<>();
        String nonceStr = Tools.createNonceStr();
        String timestamp = Tools.createTimestamp();
        String string1;
        String signature = "";
        string1 = "jsapi_ticket=" + apiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=q233l13033.iok.la:55715";


        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = Tools.byteToHex(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ret.put("url", "q233l13033.iok.la:55715");
        ret.put("apiTicket", apiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        ret.put("AppId", AppID);

        message.setData(ret);
        return message;
    }



}

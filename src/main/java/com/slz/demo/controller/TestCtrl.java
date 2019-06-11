package com.slz.demo.controller;

import com.slz.demo.VO.Message;
import com.slz.demo.VO.ReturnMessage;
import com.slz.demo.pojo.User;
import com.slz.demo.service.UserSerImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Slf4j
@RestController
public class TestCtrl {
    @Autowired
    UserSerImp userSerImp;
    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping("/redisTest1")
    public Message redisTest1(String id){
        Message message=new Message();
        String str=userSerImp.redisTest(id);
        message.setData(str);
        return message;
    }



    //Error
    @RequestMapping("/returnMessage")
    public ModelAndView  returnMessage(ReturnMessage message){
        System.out.println(
                "browser:"+message.getBrowser()
                +"ip:"+message.getRequest().getRemoteAddr()
        );
        message.getModelMap().addAttribute("test","ni hao a ");
        return new ModelAndView("Test");
    }



    @RequestMapping("/test")
    public ModelAndView test(){
        return new ModelAndView("Test");
    }

    @RequestMapping("/hello")
    public String hello() {
        return "Hello Spring Boot";
    }

    @RequestMapping(value = "/greeting")
    public ModelAndView test(ModelAndView mv) {
        mv.setViewName("greeting.html");

        mv.addObject("title","欢迎使用Thymeleaf!");
        return mv;
    }



    @RequestMapping("/cookieTestCtrl")
    public void  cookieTestCtrl(HttpServletResponse response){
        Cookie cookie=new Cookie("ymc","shidabian");
        response.addCookie(cookie);
    }



}

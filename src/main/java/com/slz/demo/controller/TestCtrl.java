package com.slz.demo.controller;

import com.slz.demo.VO.ReturnMessage;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class TestCtrl {

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

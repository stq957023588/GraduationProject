package com.slz.demo.controller;

import com.slz.demo.VO.StrategyMsg;
import com.slz.demo.pojo.Strategy;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UnifiedEntranceCtrl {
    @RequestMapping("/AdminCity")
    public ModelAndView AdminCity(){
        return new ModelAndView("AdminCity");
    }

    @RequestMapping("/Admin")
    public ModelAndView Admin(){
        return new ModelAndView("Admin");
    }


    @RequestMapping("/FeedBack")
    public ModelAndView FeedBack(){
        return new ModelAndView("FeedBack");
    }


    @RequestMapping("/Chat")
    public ModelAndView Chat(){
        return new ModelAndView("Chat");
    }


    @RequestMapping("/ForgetPassword")
    public ModelAndView ForgetPassword(String account,ModelMap modelMap){
        modelMap.addAttribute("account",account);
        return new ModelAndView("ForgetPassword");
    }

    @RequestMapping("/SearchResult")
    public ModelAndView SearchResult(String condition,ModelMap modelMap){
        modelMap.addAttribute("condition",condition);
        return new ModelAndView("SearchResult");
    }

    @RequestMapping("/bootstrap")
    public ModelAndView bootstrap(){
        return new ModelAndView("BootstrapTest");
    }

    @RequestMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("index");
    }

    @RequestMapping("/loginEnter")
    public ModelAndView login(){
        return new ModelAndView("Login");
    }

    @RequestMapping("/registerEnter")
    public ModelAndView register(){
        return new ModelAndView("Register");
    }

    @RequestMapping("/cookieTest")
    public ModelAndView cookieTest(){
        return new ModelAndView("CookieTest");
    }

    @RequestMapping("/sessionTest")
    public ModelAndView sessionTest(HttpServletRequest request){

        request.getSession().setAttribute("ymc","shidabian");

        return new ModelAndView("SessionTest");
    }

    @RequestMapping("/home")
    public ModelAndView Home(){
        return new ModelAndView("Home");
    }

    @RequestMapping("/StrategyWrite")
    public ModelAndView strategyWrite(){
        return new ModelAndView("StrategyWrite");
    }

    @RequestMapping("StrategyInfo")
    public ModelAndView getStrategById(String strategyId, ModelMap modelMap){
        modelMap.addAttribute("strategyId",strategyId);
        return new ModelAndView("StrategyInfo");
    }

}

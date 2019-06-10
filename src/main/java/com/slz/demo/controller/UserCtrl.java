package com.slz.demo.controller;

import com.slz.demo.VO.Message;
import com.slz.demo.VO.UserMsg;
import com.slz.demo.pojo.User;
import com.slz.demo.service.StrategySerImp;
import com.slz.demo.service.UserConcernSerImp;
import com.slz.demo.service.UserSerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

@RestController
public class UserCtrl {
    @Autowired
    UserSerImp userSer;
    @Autowired
    UserConcernSerImp userConcernSerImp;
    @Autowired
    StrategySerImp strategySerImp;


    @RequestMapping("/ChangeProblem")
    public ModelAndView ChangeProblem(String account,ModelMap modelMap){
        modelMap.addAttribute("problem",userSer.findByAccount(account).getProblem());
        modelMap.addAttribute("account",account);
        return new ModelAndView("ChangeProblem");
    }

    @RequestMapping("/saveProblem")
    public Message saveProblem(User userParam){
        Message message=new Message();
        User user=userSer.findByAccount(userParam.getAccount());
        user.setProblem(userParam.getProblem());
        user.setAnswer(userParam.getAnswer());
        userSer.sava(user);
        return message;
    }


    @RequestMapping("/personalPage")
    public ModelAndView personalPage(String account,String pageName,ModelMap modelMap){
        User user=userSer.findByAccount(account);
        UserMsg userMsg=new UserMsg(user);
        modelMap.addAttribute("user",userMsg);
        modelMap.addAttribute("pageName",pageName);
        return new ModelAndView("PersonalPage");
    }



    @RequestMapping("/checkProblem")
    public Message checkProblem(String account,String answer){
        Message message=new Message();
        User user=userSer.findByAccount(account);
        message.setData(user.getAnswer().equals(answer));
        return message;
    }
    
    
    @RequestMapping("/getUserNameByAccount")
    public Message getUserNameByAccount(String account ){
        Message message=new Message();
        message.setData(userSer.findByAccount(account).getName());
        return message;
    }
    

    @RequestMapping("/getUserInfoByAccount")
    public Message getUserInfoByAccount(String account){

        Message message=new Message();
        User user=userSer.findByAccount(account);
        try{
            user.setHeadPic(null);
            user.setPassword(null);
            user.setAnswer(null);
        }catch (NullPointerException e){

        }

//        user.setAccount(null);
        int fansNum=userConcernSerImp.getFansNum(account);
        UserMsg userMsg=new UserMsg(user,fansNum);
        userMsg.setStrategiesNum(strategySerImp.countByAccount(account));
        message.setData(userMsg);
        return message;
    }
    
    @RequestMapping("/loginOut")
    public Message loginOut(HttpServletRequest request){
        Message message=new Message();
        request.getSession().removeAttribute("user");//清空session信息
        request.getSession().invalidate();//清除 session 中的所有信息
        return message;
    }

    @RequestMapping("/loginCtrl")
    public Message login(User user, HttpServletRequest request){
        Message message=new Message();
        User user1 =userSer.findByAccount(user.getAccount());
        if(user1 !=null&& user.getPassword().equals(user1.getPassword())){
            message.setTip("Success");
            user1.setPassword(null);
            request.getSession().setAttribute("user",user1);
        }else {
            message.setTip("Error");
        }
        message.setData(user);
        return message;
    }




    @RequestMapping("/showHeadPic")
    public String  showHeadPic(Integer id){
        return userSer.findById(id).getHeadPic();
    }

    @RequestMapping("/showHeadPicByAccount/{account}")
    public  String  showHeadPicByAccount(@PathVariable String account){
        return userSer.findByAccount(account).getHeadPic();
    }

    @RequestMapping("/showHeadPicStrByAccount")
    public  String  showHeadPicStrByAccount(String account){
        return userSer.findByAccount(account).getHeadPic();
    }


    @RequestMapping("headPicCheck")
    public boolean headPicCheck(String account){
        if (userSer.findByAccount(account).getHeadPic()==null){
            return false;
        }else{
            return true;
        }
    }
    @RequestMapping("/changePassoword")
    public Message changePassword(String account,String newPassowrd){
        Message message=new Message();
        User user=userSer.findByAccount(account);
        user.setPassword(newPassowrd);
        userSer.sava(user);
        return message;
    }


    @RequestMapping("/accountExistCheck")
    public Message accountExistCheck(String account){
        Message message=new Message();
        User user =userSer.findByAccount(account);
        message.setData(user);
        try {
            user.setPassword(null);
        }catch (NullPointerException e){

        }
        return message;
    }
    
    @RequestMapping("/saveHeadPic")
    public Message saveHeadPic(String headPic64,String account){
        Message message=new Message();
        User user=userSer.findByAccount(account);
        user.setHeadPic(headPic64);
        userSer.sava(user);
        return message;
    }




    @RequestMapping("/saveInfo")
    public Message saveInfo(User user,String birthdayStr,HttpServletRequest request){
        Message message=new Message();
        Timestamp timestamp=Timestamp.valueOf(birthdayStr+" 00:00:00");
        User userOld=userSer.findByAccount(user.getAccount());
        userOld.setBirthday(timestamp);
        userOld.setName(user.getName());
        userOld.setSex(user.getSex());
        userSer.sava(userOld);
        request.getSession().setAttribute("user",userOld);
        return message;
    }

    @RequestMapping("/registerCtrl")
    public ModelAndView register(User user){
        Message message=new Message();
        System.out.println(user);
        try {
            user.setRegisteredTime(new Timestamp(System.currentTimeMillis()));
            userSer.sava(user);
        } catch (Exception e){
            message.setData("error");
            e.printStackTrace();
        }
        return new ModelAndView("Login");
    }
}

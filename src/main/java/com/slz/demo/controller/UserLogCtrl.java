package com.slz.demo.controller;

import com.slz.demo.VO.Message;
import com.slz.demo.enums.PromptEnum;
import com.slz.demo.pojo.User;
import com.slz.demo.pojo.UserConcern;
import com.slz.demo.pojo.UserLog;
import com.slz.demo.service.UserConcernSerImp;
import com.slz.demo.service.UserLogSerImp;
import com.slz.demo.service.UserSerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserLogCtrl {
    @Autowired
    UserLogSerImp userLogSerImp;
    @Autowired
    UserConcernSerImp userConcernSerImp;
    @Autowired
    UserSerImp userSerImp;

    //检查当前用户是否关注该作者
    @RequestMapping("/checkConcernOrNot")
    public Message checkConcernOrNot(String account,HttpServletRequest request){
        Message message=new Message();
        Integer userId=null;
        try{
            userId=((User)request.getSession().getAttribute("user")).getId();
            message.setData(userConcernSerImp.findByAuthorAccountAndUserId(account,userId));
        }catch (NullPointerException e){
            message.setData(false);
        }
        return message;
    }

    //关注该用户
    @RequestMapping("/concernUser")
    public Message concernUser(String account,HttpServletRequest request){
        Message message=new Message();
        Integer userId=null;
        try{
            userId=((User)request.getSession().getAttribute("user")).getId();
        }catch (NullPointerException e){
            message.setTip(PromptEnum.loginFirst.getMessage());
            return  message;
        }
        message.setTip(PromptEnum.hvaeBeenConcerned.getMessage());
        if (userConcernSerImp.findByAuthorAccountAndUserId(account,userId)){
            System.out.println("2\t"+message);
            return  message;
        }
        UserConcern userConcern=new UserConcern();
        userConcern.setUserId(userId);
        userConcern.setAuthorAccount(account);
        userConcernSerImp.save(userConcern);
        message.setTip("已关注");
        System.out.println("1\t"+message);
        return message;
    }
    //取消关注该用户
    @RequestMapping("/cancelConcern")
    public Message cancelConcern(String account,HttpServletRequest request){
        Message message=new Message();
        Integer userId=null;
        try{
            userId=((User)request.getSession().getAttribute("user")).getId();
        }catch (NullPointerException e){
            message.setTip(PromptEnum.loginFirst.getMessage());
            return  message;
        }
        userConcernSerImp.deleteByAuthorAccountAndUserId(account,userId);
        message.setTip("取消关注");
        return message;
    }

    //获取用户粉丝数
    @RequestMapping("/getFansNum")
    public Message getFansNum(String account){
        Message message=new Message();
        int num=userConcernSerImp.getFansNum(account);
        message.setData(num);
        return message;
    }
    //获取用户关注数
    @RequestMapping("/getConcernsNum")
    public Message getConcernsNum(String account){
        Message message=new Message();
        User u=userSerImp.findByAccount(account);
        try{
            int num=userConcernSerImp.getConcernedNum(u.getId());
            message.setData(num);
        }catch (NullPointerException e){
            message.setTip("error");
        }
        return message;
    }
    //获取用户粉丝
    @RequestMapping("/getFansInfo")
    public Message getFansInfo(String account,Integer page,Integer size){
        Message message=new Message();
        List<UserConcern> concernList=userConcernSerImp.findByAuthorAccount(account);
        List<Integer> userIdList=concernList.stream()
                .map(e->e.getUserId())
                .collect(Collectors.toList());
        List<User> userList=userSerImp.findByIdList(page,size,userIdList);
        userList.stream().forEach(e->e.setPassword(null));
        message.setData(userList);
        return message;
    }
    //获取用户关注
    @RequestMapping("/getConcernsInfo")
    public Message getConcernsInfo(String  account,int page,int size){
        Message message=new Message();
        User u=userSerImp.findByAccount(account);
        List<UserConcern> concernList=userConcernSerImp.findByUserId(u.getId());
        List<String > accountList=concernList.stream()
                .map(e->e.getAuthorAccount())
                .collect(Collectors.toList());
        List<User> userList=userSerImp.findByAccountList(page,size,accountList);
        userList.stream().forEach(e->e.setPassword(null));
        message.setData(userList);
        return message;
    }


    //检查该用户是否已经收藏火点赞该攻略
    @RequestMapping("strategyInfoCheck")
    public Message strategyInfoCheck(String strategyId, String operation, HttpServletRequest request){
        Message message=new Message();
        Integer userId=null;
        try{
            userId=((User)request.getSession().getAttribute("user")).getId();
        }catch (NullPointerException e){
//            e.printStackTrace();
        }
        UserLog nullCheck=userLogSerImp.findByUserIdAndStrategyIdAndAndOperationId(userId,strategyId,operation);
        if(nullCheck==null){
            message.setTip("null");
        }else {
            message.setTip("have");
        }

        return message;

    }


    @RequestMapping("/CollectUpgood")
    public Message  CollectUpgood(String strategyId,String operation,HttpServletRequest request){
        Message message=new Message();
        Integer userId=null;
        try{
            userId=((User)request.getSession().getAttribute("user")).getId();
        }catch (NullPointerException e){
            message.setTip("Please login in first");
            return  message;
        }
        UserLog nullChcek=userLogSerImp.findByUserIdAndStrategyIdAndAndOperationId(userId,strategyId,operation);
        if (nullChcek==null){
            UserLog userLog=new UserLog();
            userLog.setUserId(userId);
            userLog.setStrategyId(strategyId);
            userLog.setOperationId(operation);
            userLogSerImp.save(userLog);
            message.setTip("Operate success");
        }else {
            userLogSerImp.delete(nullChcek);
            message.setTip("cancel");
        }
        return message;
    }
}

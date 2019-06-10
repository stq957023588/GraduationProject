package com.slz.demo.controller;


import com.slz.demo.VO.ChatMsg;
import com.slz.demo.VO.Message;
import com.slz.demo.pojo.Chat;
import com.slz.demo.pojo.User;
import com.slz.demo.service.ChatSerImp;
import com.slz.demo.service.UserSerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@RestController
public class ChatCtrl {
    @Autowired
    ChatSerImp chatSerImp;
    @Autowired
    UserSerImp userSerImp;
    
    @RequestMapping("/HasRead")
    public Message HasRead(String account1,String account2){
        Message message=new Message();
        chatSerImp.isRead(account1,account2);
        return message;
    }

    @RequestMapping("/getAllUnreadNum")
    public Message getAllUnreadNum(String account){
        Message message=new Message();
        message.setData(chatSerImp.getUnreadNum(account));
        return message;
    }

    @RequestMapping("/getUnreadNumByAccount")
    public Message getUnreadNumByAccount(String account1,String account2){
        Message message=new Message();
        message.setData(chatSerImp.getUnreadNumByAccount(account1,account2));
        return message;
    }
    

    @RequestMapping("/getChatUser")
    public Message getChatUser(String account){
        Message message=new Message();
        message.setData(chatSerImp.getChatUsersByAccount(account));
        return message;
    }

    @RequestMapping("/getChatContent")
    public Message getChatContent(String account1,String account2){
        Message message=new Message();
        List<ChatMsg> msgList=new ArrayList<>();
        chatSerImp.getChat(account1,account2)
                .stream()
                .forEach(new Consumer<Chat>() {
                    @Override
                    public void accept(Chat chat) {
                        ChatMsg chatMsg=new ChatMsg();
                        chatMsg.setUser1(userSerImp.findByAccount(chat.getFromAccount()));
                        chatMsg.setUser2(userSerImp.findByAccount(chat.getToAccount()));
                        chatMsg.setContent(chat.getContent());
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        chatMsg.setTime(sdf.format(chat.getTime()));
                        msgList.add(chatMsg);
                    }
                });
        message.setData(msgList);
        return message;
    }

    @RequestMapping("/saveChat")
    public Message saveChat(String account1,String account2,String content){
        Message message=new Message();
        chatSerImp.save(account1,account2,content);
        return message;
    }


    @RequestMapping("/saveChatM")
    public Message saveChatM(String account, HttpServletRequest request){
        Message message=new Message();
        String MyAccount=null;
        try{
            User user=(User)request.getSession().getAttribute("user");
            MyAccount=user.getAccount();
            chatSerImp.save(MyAccount,account,null);
        }catch (NullPointerException e){
            message.setData("请登陆");
            message.setTip("error");
        }

        return message;
    }

}

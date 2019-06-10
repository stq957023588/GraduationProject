package com.slz.demo.service;

import com.slz.demo.VO.ChatMsg;
import com.slz.demo.dao.ChatAidedDao;
import com.slz.demo.dao.ChatDao;
import com.slz.demo.dao.UserDao;
import com.slz.demo.pojo.Chat;
import com.slz.demo.pojo.ChatAided;
import com.slz.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Service
public class ChatSerImp implements ChatSer {

    @Autowired
    ChatAidedDao chatAidedDao;
    @Autowired
    ChatDao chatDao;
    @Autowired
    UserDao userDao;

    public void isRead(String account1,String account2){
        ChatAided chatAided=chatAidedDao.checkByChatMembersAccount(account1,account2);
        chatDao.updateIsRead(chatAided.getId());
    }

    public Integer getUnreadNum(String account ){
        return chatDao.countByToAccountAndIsRead(account,0);
    }

    public Integer getUnreadNumByAccount(String account1,String account2){
        return chatDao.countByToAccountAndFromAccountAndIsRead(account1,account2,0);
    }


    public Chat save(String account1,String account2,String content) {
        ChatAided chatAided=chatAidedDao.checkByChatMembersAccount(account1,account2);
        if( chatAided==null){
            chatAided=new ChatAided();
            chatAided.setAccount1(account1);
            chatAided.setAccount2(account2);
            chatAidedDao.save(chatAided);
        }
        Chat chat=null;
        if(content!=null){
             chat=new Chat();
            chat.setFromAccount(account1);
            chat.setToAccount(account2);
            chat.setChatIdentity(chatAided.getId());
            chat.setContent(content);
            chatDao.save(chat);
        }

        return chat;
    }

    public List<Chat> getChat(String account1,String account2){
        ChatAided chatAided=chatAidedDao.checkByChatMembersAccount(account1,account2);
        if(chatAided==null){
            return null;
        }
        List<Chat> chatList=chatDao.findByChatIdentityOrderByTime(chatAided.getId());
        return chatList;
    }



    public List<User> getChatUsersByAccount(String account){
        List<User> userList=new ArrayList<>();
        chatAidedDao.findByAccount1AndIsShowOrAccount2(account,1,account)
                .stream()
                .forEach(new Consumer<ChatAided>() {
                    @Override
                    public void accept(ChatAided chatAided) {
                        User user=null;
                        if(!account.equals(chatAided.getAccount1())){
                            user=userDao.findByAccount(chatAided.getAccount1());
                        }else{
                            user=userDao.findByAccount(chatAided.getAccount2());
                        }
                        user.setAnswer(null);
                        user.setPassword(null);
                        userList.add(user);
                    }
                });
        return userList;
    }


}

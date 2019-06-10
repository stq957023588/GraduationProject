package com.slz.demo.controller;

import com.slz.demo.VO.CommentMsg;
import com.slz.demo.VO.Message;
import com.slz.demo.enums.ErrorEnum;
import com.slz.demo.enums.PromptEnum;
import com.slz.demo.pojo.Comment;
import com.slz.demo.pojo.User;
import com.slz.demo.service.CommentSerImp;
import com.slz.demo.service.UserSerImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CommentCtrl {
    @Autowired
    CommentSerImp commentSerImp;
    @Autowired
    UserSerImp userSerImp;

    @RequestMapping("/getStrategyCommentCount")
    public Message getStrategyCommentCount(String strategyId){
        Message message=new Message();
        message.setData(commentSerImp.countStrategyIdOrderByTime(strategyId));
        System.out.println(strategyId+"\t"+message);
        return message;
    }

    @RequestMapping("/getUserCommentCount")
    public Message getUserCommentCount(String account){
        Message message=new Message();
        message.setData(commentSerImp.countUserAccountOrderByTime(account));
        return message;
    }


    @RequestMapping("/getStrategyCommentSort")
    public Message getStrategyCommentSort(int pageNum,int pageSize,String strategyId){
        Message message=new Message();
        List<CommentMsg> msgList=commentSerImp.findByStrategyIdOrderByTime(PageRequest.of(pageNum-1,pageSize),strategyId)
                .stream()
                .map(e->new CommentMsg(e))
                .collect(Collectors.toList());
        msgList.stream()
                .forEach(e->e.setUserName(userSerImp.findByAccount(e.getComment().getUserAccount()).getName()));
        message.setData(msgList);
        return message;
    }

    @RequestMapping("/getStrategyComment")
    public Message getStrategyComment(String strategyId){
        Message message=new Message();
        List<CommentMsg> msgList=commentSerImp.findByStrategyIdOrderByTime(strategyId)
                .stream()
                .map(e->new CommentMsg(e))
                .collect(Collectors.toList());
        msgList.stream()
                .forEach(e->e.setUserName(userSerImp.findByAccount(e.getComment().getUserAccount()).getName()));
        message.setData(msgList);
        return message;
    }

    @RequestMapping("/saveComment")
    public Message saveComment(HttpServletRequest request,String strategyId,String commentContent){
        Message message=new Message();

        String  account=null;
        try{
            account=((User)request.getSession().getAttribute("user")).getAccount();
        }catch (NullPointerException e){
            message.setTip(ErrorEnum.error1.getCode());
            return  message;
        }
        Comment comment=new Comment();
        comment.setCommentContent(commentContent);
        comment.setStrategyId(strategyId);
        comment.setUserAccount(account);

        commentSerImp.save(comment);
        CommentMsg msg=new CommentMsg(comment);
        msg.setUserName(userSerImp.findByAccount(msg.getComment().getUserAccount()).getName());
        message.setData(msg);
        message.setTip(PromptEnum.commentSuccess.getMessage());
        return message;
    }
}

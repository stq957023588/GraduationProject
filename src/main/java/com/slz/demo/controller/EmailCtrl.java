package com.slz.demo.controller;


import com.slz.demo.VO.Message;
import com.slz.demo.utils.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailCtrl {
    @Value("${custom.email.smtpHost}")
    private String SmtpHost;
    @Value("${custom.email.account}")
    private String SenderName;
    @Value("${custom.email.password}")
    private String SenderPass;
    @Value("${custom.email.receive}")
    private String Receive;

    @RequestMapping("/callEmailService")
    public Message callEmailService(String content){
        Message message=new Message();
        Email email=null;

        email=Email.noAttatachmentEntity(SmtpHost,SenderName,SenderPass,Receive,"意见反馈",content);

        try {
            email.send();
        } catch (Exception e) {
            System.err.println("Email Error");
            message.setTip("Error");
        }

        return message;
    }
    
    
}

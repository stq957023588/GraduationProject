package com.slz.demo.pojo;


import com.slz.demo.enums.PrefixEnum;
import com.slz.demo.utils.Tools;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "chat")
public class Chat implements Serializable {
    @Id
    private String id;
    private String fromAccount;
    private String toAccount;
    private String content;
    private Integer isRead=0;
    private Timestamp time;
    private String chatIdentity;

    public Chat(){
        id= Tools.getId(PrefixEnum.ChatIdPrefix.getMessage());
        time=new Timestamp(System.currentTimeMillis());
    }



}

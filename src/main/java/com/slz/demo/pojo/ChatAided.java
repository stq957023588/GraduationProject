package com.slz.demo.pojo;

import com.slz.demo.enums.PrefixEnum;
import com.slz.demo.utils.Tools;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "chat_aided")
public class ChatAided {

    @Id
    private String id;
    private String account1;
    private String account2;
    private Integer isShow=1;
    public ChatAided(){
        id= Tools.getId(PrefixEnum.ChatAidedIdPrefix.getMessage());
    }


}

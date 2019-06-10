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
@Table(name = "strategy_comments")
public class Comment {
    @Id
    private String id;
    private String strategyId;
    private String userAccount;
    private String commentContent;
    private Timestamp time;

    public Comment(){
        id= Tools.getId(PrefixEnum.CommentIdPrefix.getMessage());
        time=new Timestamp(System.currentTimeMillis());
    }
}

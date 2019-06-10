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
@Table(name = "user_log")
public class UserLog {
    @Id
    private String id;
    private Integer userId;
    private String strategyId;
    private String  operationId;
    private Timestamp time;
    private String ip;

    public UserLog(){
        id= Tools.getId(PrefixEnum.ReportLogIdPrefix.getMessage());
        time=new Timestamp(System.currentTimeMillis());
    }
}

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
@Table(name = "users_concerns")
public class UserConcern {
    @Id
    private String id;
    private Integer userId;
    private String authorAccount;
    private Timestamp time;

    public UserConcern(){
        id= Tools.getId(PrefixEnum.UserConcernIdPrefix.getMessage());
        time=new Timestamp(System.currentTimeMillis());
    }

}

package com.slz.demo.pojo;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@ConfigurationProperties(prefix = "pojo.user")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String account;
    private String password;
    private String name;
    private String headPic;
    private String sex="保密";
    private Timestamp birthday;
    private Timestamp registeredTime;
    private Integer onlineState=-1;
    private Integer isSysAdmin=0;
    private String problem;
    private String answer;


    public void safe(){
        this.setPassword(null);
        this.setAnswer(null);
    }

}

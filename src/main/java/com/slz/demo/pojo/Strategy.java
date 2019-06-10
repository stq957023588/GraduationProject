package com.slz.demo.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Entity
@ConfigurationProperties(prefix = "pojo.Strategy")
@Table(name = "strategies")
public class Strategy  implements Serializable{
    @Id
    private String id;

    private String account;
    private String cityId;
    private String title;
    private String content;
    private String contentText;
    private String attractionsName;
    private Integer readingVolume=0;
    private Timestamp publishTime;
    private Integer isLegal=1; // 1 合法   0 审核中    -1不合法
    private Integer beReported=0; //0未被举报   1 已被举报

    public Strategy(){
        publishTime=new Timestamp(System.currentTimeMillis());
    }

}

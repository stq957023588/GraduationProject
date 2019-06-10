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
@Table(name="report_log")
public class ReportLog {
    @Id
    private String id;
    private Integer userId;
    private String  strategyId;
    private Integer reportType;
    private Timestamp time;
    public ReportLog(){
        id= Tools.getId(PrefixEnum.ReportLogIdPrefix.getMessage());
        time=new Timestamp(System.currentTimeMillis());
    }
}

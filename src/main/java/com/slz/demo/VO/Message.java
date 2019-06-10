package com.slz.demo.VO;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Message<T> {
    private String className;
    private String methodName;
    private T data;
    private String tip;
    private String time;
    public Message(){
        StackTraceElement temp=Thread.currentThread().getStackTrace()[2];
        this.methodName = temp.getMethodName();
        this.className=temp.getClassName();
        this.tip="success";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.time=sdf.format(new Date());
    }
}

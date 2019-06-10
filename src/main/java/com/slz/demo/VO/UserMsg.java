package com.slz.demo.VO;

import com.slz.demo.pojo.User;
import lombok.Data;

import javax.jws.soap.SOAPBinding;
import java.text.SimpleDateFormat;

@Data
public class UserMsg {
    private User user;
    private int fansNum;
    private int strategiesNum;
    private String birthdayStr;
    private String registeredTimeStr;
    public UserMsg(User user,int fansNum){
        this.fansNum=fansNum;
        this.user=user;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try{
            this.birthdayStr=sdf.format(user.getBirthday());
            this.registeredTimeStr=sdf.format(user.getRegisteredTime());
        }catch (NullPointerException e){

        }

    }
    public UserMsg(User user){
        this.user=user;
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        try{
            this.birthdayStr=sdf.format(user.getBirthday());
            this.registeredTimeStr=sdf.format(user.getRegisteredTime());
        }catch (NullPointerException e){

        }
    }
}

package com.slz.demo.VO;

import com.slz.demo.pojo.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ChatMsg {
    private User user1;
    private User user2;

    private String content;


    private String time;
}

package com.slz.demo.VO;

import com.slz.demo.pojo.User;
import lombok.Data;

@Data
public class ChatMemberMsg {

    private String id;
    private User user1;
    private User user2;

}

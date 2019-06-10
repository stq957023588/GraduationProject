package com.slz.demo.VO;

import com.slz.demo.pojo.Comment;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
public class CommentMsg {
    private String userName;
    private Comment comment;
    private String timeStr;
    public CommentMsg(Comment comment){
        this.comment=comment;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        timeStr=sdf.format(comment.getTime());
    }
}

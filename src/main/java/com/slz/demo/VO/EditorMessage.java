package com.slz.demo.VO;

import lombok.Data;

import java.util.List;

@Data
public class EditorMessage {
    private Integer errno=0;
    private List<String > data;
}

package com.slz.demo.VO;

import lombok.Data;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;

@Data
public class ReturnMessage {
    private String browser;
    private HttpServletRequest request;
    private ModelMap modelMap;
}

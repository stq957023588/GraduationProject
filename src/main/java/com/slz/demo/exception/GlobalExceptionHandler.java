package com.slz.demo.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

//@CrossOrigin
//@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler
    public ModelAndView exceptionHandle(HttpServletRequest req, HttpServletResponse res, Exception e){
        return new ModelAndView("Error","reason",e.getMessage());
    }
}
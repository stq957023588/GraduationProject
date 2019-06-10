package com.slz.demo.controller;

import com.slz.demo.VO.EditorMessage;
import com.slz.demo.enums.PathEnum;
import com.slz.demo.utils.Tools;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.tools.Tool;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class LocalizationCtrl {

    @RequestMapping("/picLocalization")
    public EditorMessage picLocalization(@RequestParam("file") List<MultipartFile> list){
        EditorMessage message=new EditorMessage();
        List<String> urls = new ArrayList<>();
        if (list.size() == 0) {
            message.setErrno(1);
        }
        Integer picCount=null;
        for (MultipartFile file : list) {
            picCount=Tools.getPicCount();
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String timeStr=sdf.format(date)+"_";
            String path= PathEnum.tempPathPrefix.getMessage()+timeStr+ picCount+PathEnum.jpg.getMessage();
            File tempFile=new File(path);
            urls.add(Tools.toServerPath(timeStr+ picCount));
            FileOutputStream fos;
            try {
                fos=new FileOutputStream(tempFile);
                fos.write(file.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        message.setData(urls);
        return message;
    }

    @RequestMapping("/picOnServer")
    public byte[] picOnServer(String fileName){
        String path=PathEnum.tempPathPrefix.getMessage()+fileName;
        File file=new File(path);
        InputStream is=null;
        try {
            is=new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] img=null;
        try {
            img=Tools.stramToByteArray(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

}

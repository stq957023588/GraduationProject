package com.slz.demo.controller;

import com.slz.demo.VO.Message;
import com.slz.demo.enums.PrefixEnum;
import com.slz.demo.pojo.City;
import com.slz.demo.service.CitySerImp;
import com.slz.demo.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
public class CityCtrl {

    @Autowired
    CitySerImp ser;

    @RequestMapping("/AddPosition")
    public Message AddPosition(City city){
        Message message=new Message();
        city.setId(Tools.getId(PrefixEnum.CityPrefix.getMessage()));
        ser.save(city);
        try {
            ser.databaseToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }


    @RequestMapping("/showContinent")
    public ModelAndView findContinent(ModelMap modelMap){
//        modelMap.addAttribute("continents",citySerImp.findContinent());
        return new ModelAndView("ContinentSelect");
    }

    @RequestMapping("/CountrySelectCtrl")
    public ModelAndView countrySelectCtrl(){
        return new ModelAndView("CountrySelect");
    }

    @RequestMapping("/showCountry")
    public Message findAndPageingByContinent(int page,int size,String continent){
        Message message=new Message();
        System.out.println(continent);
        List<String> list= ser.findAndPageingByContinent(page,size,continent);
        message.setData(list);
        return message;
    }



    @RequestMapping("/sortPage")
    public Message findAll(Integer page,Integer size){
        Message message=new Message();
        try{
            PageRequest request=PageRequest.of(page,size);
            Page<City> attractionsInfoPage= ser.findAll(request);
            List<City> list= attractionsInfoPage.getContent();
            message.setData(list);

        }catch (Exception e){
            e.printStackTrace();
            message.setTip("failed");
        }
        return message;
    }

    @RequestMapping("/saveAttractions")
    public Message save(City city){
        Message msg=new Message();
        try{
            ser.save(city);
            System.out.println(city);
        }catch (Exception e){
            e.printStackTrace();
            msg.setTip("failed");
        }
        return msg;
    }
}

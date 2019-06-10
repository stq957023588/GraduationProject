package com.slz.demo.service;

import com.slz.demo.pojo.City;
import com.slz.demo.pojo.User;
import com.slz.demo.utils.Tools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CitySerImpTest {

    @Autowired
    private CitySerImp attractionsSer;

    @Test
    public void FuzzyScreen(){
        Tools.getListContent(attractionsSer.FuzzyScreen("雅木猹"));
    }



    @Test
    public void save() {
        City a=new City();
        a.setId("bbbb0001");
        attractionsSer.save(a);
    }

    @Test
    public void delete() {


    }

    @Test
    public void deleteByIdInBatch() {
    }

    @Test
    public void findAll() {
    }

    @Test
    public void findAll1() {
    }


    @Test
    public void userTest(){
        User user=new User();
        System.out.println(user);
    }


    @Test
    public void findAndPagingByContinent(){
        String continent="Europe";
        List<String> list=attractionsSer.findAndPageingByContinent(1,5,continent);
//        List<City> list=attractionsSer.findAndPageingByContinent(0,20,continent);
        for (String  a :
                list) {
            System.out.println(a);
        }
    }

    @Test
    public void Screening(){
        Map<String ,String > paramMap=new HashMap<>();
        paramMap.put("continent","Europe");
        paramMap.put("country","Frankreich");
        paramMap.put("province","Paris");
        paramMap.put("city","Paris");
        Tools.getListContent(attractionsSer.Screening(paramMap));
    }

}
package com.slz.demo.dao;

import com.alibaba.fastjson.JSON;
import com.slz.demo.enums.PathEnum;
import com.slz.demo.pojo.City;
import com.slz.demo.pojo.Strategy;
import com.slz.demo.utils.Tools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CityDaoTest {
    @Autowired
    private CityDao cityDao;

    @Test
    public void save(){
        City a=new City();
        a.setId("ssss0001");
        cityDao.save(a);
    }

    @Test
    public void findOne(){
        City a = cityDao.findById("ssss0001").orElse(null);
        System.out.println(a);

    }

    @Test
    public void findAll(){
        PageRequest request=PageRequest.of(1,2);

        //oracle 11g can't sort page
        Page<City> productInfoPage= cityDao.findAll(request);
        System.out.println(productInfoPage.getContent());
    }



    @Test
    public void delete(){
        City city =new City();
        city.setId("yzzj0002");
        cityDao.delete(city);
    }

    @Test
    public void deleteById(){
        List<String> list=new ArrayList<>();
//        City a1=new City();
//        a1.setId("yzzj0002");
//        City a2=new City();
//        a2.setId("yzzj0003");
        list.add("yzzj0004");
        list.add("yzzj0003");
        cityDao.deleteByIdInBatch(list);
    }


    @Test
    public void findContinent(){
        List<String> list= cityDao.findContinent();
        for (String s:
             list) {
            System.out.println(s);
        }
    }

    @Test
    public void findStr(){
        List<String> list= cityDao.findCountry("Asia");
        for (String s:
                list) {
            System.out.println("country:"+s);
        }
    }

    @Test
    public void findAndPagingByContinent(){
        List<String> list= cityDao.findAndPageingByContinent(1,10,"Europe");
        for (String s:
                list) {
            System.out.println("country:"+s);
        }
    }


    @Test
    public void findByContinentAndCountry(){
        Tools.getListContent(cityDao.findByContinentAndCountry("Europe",null));
    }


    @Test
    public void findCityByCityEndsWith(){
        System.out.println(cityDao.findCityByCityEndsWith("u"));
    }

    @Test
    public void databaseToFile() throws IOException {
        List<String > continents;
        Map<String ,List<String >> countries=new HashMap<>();
        Map<String ,List<String >> provinces=new HashMap<>();
        Map<String ,List<String >> cities=new HashMap<>();


        continents=cityDao.findContinent();
        for (String continent:continents){
            List<String > countryList=cityDao.findCountry(continent);
            countries.put(continent,countryList);

            for(String country:countryList){
                List<String > provinceList=cityDao.findProvince(continent,country);
                provinces.put(country,provinceList);

                for (String province:provinceList){
                    List<String > cityList=cityDao.findCity(continent,country,province);
                    cities.put(province,cityList);
                }
            }
        }
        Map<String ,List<String >> continentMap=new HashMap<>();
        continentMap.put("continent",continents);
        Tools.writeJsonToLocal(continentMap,PathEnum.tempPathPrefix.getMessage()+"continent.json");
        Tools.writeJsonToLocal(countries,PathEnum.tempPathPrefix.getMessage()+"country.json");
        Tools.writeJsonToLocal(provinces,PathEnum.tempPathPrefix.getMessage()+"province.json");
        Tools.writeJsonToLocal(cities,PathEnum.tempPathPrefix.getMessage()+"city.json");


    }

}
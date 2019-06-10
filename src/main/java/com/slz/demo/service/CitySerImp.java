package com.slz.demo.service;

import com.slz.demo.dao.CityDao;
import com.slz.demo.enums.PathEnum;
import com.slz.demo.pojo.City;
import com.slz.demo.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.*;

@Service
public class CitySerImp implements CitySer {
    @Autowired
    CityDao cityDao;

    @Override
    public void save(City city) {
        cityDao.save(city);
    }

    @Override
    public void delete(City city) {
        cityDao.delete(city);
    }

    @Override
    public void deleteByIdInBatch(List<String> list) {
        cityDao.deleteByIdInBatch(list);
    }

    @Override
    public List<City> findAll() {
        return cityDao.findAll();
    }

    @Override
    public Page<City> findAll(PageRequest pageRequest) {
        return cityDao.findAll(pageRequest);
    }

    @Override
    public List<String> findAndPageingByContinent(int page,int size,String continent) {
        int start=1+size*(page-1);
        int end=size*page;
        return cityDao.findAndPageingByContinent(start,end,continent);

//        Pageable pageable=PageRequest.of(page,size, Sort.Direction.ASC,"id");
//                Specification<String> specification = new Specification<String>() {
//            @Override
//            public Predicate toPredicate(Root<City> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
////                Predicate predicate=criteriaBuilder.conjunction();
////                predicate=criteriaBuilder.equal(root.get("continent").as(String.class),continent);
//                criteriaQuery.groupBy(root.get("country"));
////                List<Predicate> list=new ArrayList<>();
////                list.add(criteriaBuilder.equal(root.get("continent").as(String.class),continent));
////                list.add(criteriaQuery.groupBy(root.get("country")).getRestriction());
////                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
//                return criteriaQuery.getRestriction();
//
//            }
//        };

//        Page<String > pageList=cityDao.findAll(specification,pageable);

//        return pageList.getContent();
    }

    @Override
    public City findById(String id) {
        return cityDao.findById(id).orElse(null);
    }

    @Override
    public List<String> findContinent() {
        return cityDao.findContinent();
    }




    @Override
    public List<City> Screening(Map<String ,String > paramMap) {
        Specification<City> specification=new Specification<City>() {
            @Override
            public Predicate toPredicate(Root<City> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                Set<String > keySet=paramMap.keySet();
                for (String  key:keySet){
                    list.add(criteriaBuilder.equal(root.get(key).as(String.class),paramMap.get(key)));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        return cityDao.findAll(specification);
    }

    @Override
    public List<City> FuzzyScreen(String condition) {
        Specification<City> specification=new Specification<City>() {
            @Override
            public Predicate toPredicate(Root<City> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list=new ArrayList<>();
                list.add(criteriaBuilder.like(root.get("continent").as(String .class),"%"+condition+"%"));
                list.add(criteriaBuilder.like(root.get("country").as(String .class),"%"+condition+"%"));
                list.add(criteriaBuilder.like(root.get("province").as(String .class),"%"+condition+"%"));
                list.add(criteriaBuilder.like(root.get("city").as(String .class),"%"+condition+"%"));

                return criteriaBuilder.or(list.toArray(new Predicate[list.size()]));
            }
        };

        return cityDao.findAll(specification);
    }

    @Override
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
        Resource resource = new ClassPathResource("/static/json");
        String path=resource.getFile().getPath();
        System.out.println(path+"\\continent.json");
        Tools.writeJsonToResources(continentMap, path+"\\continent.json");
        Tools.writeJsonToResources(countries,path+"\\country.json");
        Tools.writeJsonToResources(provinces,path+"\\province.json");
        Tools.writeJsonToResources(cities,path+"\\city.json");

        Tools.writeJsonToResources(continentMap, PathEnum.JsonPath.getMessage()+"continent.json");
        Tools.writeJsonToResources(countries,PathEnum.JsonPath.getMessage()+"country.json");
        Tools.writeJsonToResources(provinces,PathEnum.JsonPath.getMessage()+"province.json");
        Tools.writeJsonToResources(cities,PathEnum.JsonPath.getMessage()+"city.json");
    }
}

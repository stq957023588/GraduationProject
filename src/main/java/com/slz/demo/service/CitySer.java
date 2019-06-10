package com.slz.demo.service;

import com.slz.demo.pojo.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CitySer {

    public void save(City city);

    public void delete(City city);

    public void deleteByIdInBatch(List<String > list);

    List<City> findAll();

    Page<City> findAll(PageRequest pageRequest);

    List<String > findAndPageingByContinent(int page,int size,String continent);

    City findById(String id);

    List<String > findContinent();

    List<City> Screening(Map<String ,String > paramMap);

    List<City> FuzzyScreen(String condition);

    void databaseToFile()throws IOException;
}

package com.slz.demo.dao;

import com.slz.demo.pojo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CityDao extends JpaRepository<City,String>,JpaSpecificationExecutor<City>{
    List<Object> findCityByCityEndsWith(String string);

    List<City> findByContinent(String continent);

    List<City> findByContinentAndCountry(String continent, String country);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cities WHERE id in(?1)", nativeQuery = true)
    void deleteByIdInBatch(List<String> list);


    @Transactional
    @Modifying
    @Query(value = "select continent from cities group by continent", nativeQuery = true)
    List<String> findContinent();

    @Transactional
    @Modifying
    @Query(value = "select country from cities WHERE continent=(?1) group by country ", nativeQuery = true)
    List<String> findCountry(String continent);

    @Transactional
    @Modifying
    @Query(value = "select province from cities WHERE continent=(?1) AND country=(?2) group by province", nativeQuery = true)
    List<String> findProvince(String continent,String country);

    @Transactional
    @Modifying
    @Query(value = "select city from cities WHERE continent=(?1) AND country=(?2) AND province=(?3) group by city", nativeQuery = true)
    List<String> findCity(String continent,String country,String province);





    @Transactional
    @Modifying
    @Query(value = "select country\n" +
            "from(\n" +
            "    select country,rownum as row_num\n" +
            "    from (select country from cities where continent=(?3) group by country order by COUNTRY) a \n" +
            "    where rownum <=(?2)\n" +
            "    )\n" +
            "where row_num>=(?1)",
            nativeQuery = true)
    List<String> findAndPageingByContinent(int start,int end,String continent);

}

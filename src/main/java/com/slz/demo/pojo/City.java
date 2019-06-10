package com.slz.demo.pojo;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cities")
public class City extends Model{

    @Id
    private String id;
    private String continent;
    private String country;
    private String province;
    private String city;

}

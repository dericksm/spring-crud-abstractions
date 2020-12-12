package com.derick.entities.dto;

import com.derick.entities.City;

import java.io.Serializable;

public class CityDTO implements Serializable, BaseDTO {

    private Integer id;
    private String name;

    public CityDTO() {
    }

    public CityDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityDTO(City city) {
        this.id = city.getId();
        this.name = city.getName();
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.derick.entities.dto;

import com.derick.services.validation.ClientUpdate;

import java.io.Serializable;

@ClientUpdate
public class OrderDTO implements Serializable, BaseDTO {

    private Integer id;

    private String name;

    private Double price;

    public OrderDTO() {
    }

    public OrderDTO(Integer id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

package com.derick.entities.dto;

import com.derick.entities.State;

import java.io.Serializable;

public class StateDTO implements Serializable, BaseDTO {

    private Integer id;
    private String name;

    public StateDTO() {
    }

    public StateDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public StateDTO(State state) {
        this.id = state.getId();
        this.name = state.getName();
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

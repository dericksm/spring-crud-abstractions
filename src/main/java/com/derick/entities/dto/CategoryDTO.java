package com.derick.entities.dto;

import com.derick.entities.Category;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoryDTO implements Serializable, BaseDTO {

    private Integer id;

    @NotEmpty(message = "Can not be empty")
    @Length(min = 5, max = 80, message = "Size needs to be between 5 and 90 characters")
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id, String nome) {
        this.id = id;
        this.name = nome;
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

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

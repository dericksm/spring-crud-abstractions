package com.derick.controllers;

import com.derick.entities.Category;
import com.derick.services.AbstractService;
import com.derick.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AbstractController<T> {

    private final AbstractService<T> service;

    public AbstractController(AbstractService<T> service) {
        this.service = service;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<T> findById(@PathVariable Integer id){
        T category = service.findById(id);
        return ResponseEntity.ok().body(category);
    }
}

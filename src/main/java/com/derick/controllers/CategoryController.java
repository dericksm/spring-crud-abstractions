package com.derick.controllers;

import com.derick.entities.Category;
import com.derick.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController extends AbstractController<Category>{

    @Autowired
    public CategoryController(CategoryService service) {
        super(service);
    }
}

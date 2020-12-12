package com.derick.controllers;

import com.derick.entities.Category;
import com.derick.entities.dto.CategoryDTO;
import com.derick.services.AbstractService;
import com.derick.services.CategoryService;
import com.fasterxml.jackson.core.json.async.NonBlockingJsonParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/categories")

public class CategoryController extends AbstractController<Category, CategoryDTO> {

    @Autowired
    private CategoryService categoryService;

    @Override
    public AbstractService<Category, CategoryDTO> getService() {
        return categoryService;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Category> insert(@Valid @RequestBody CategoryDTO objDTO) {
        return super.insert(objDTO);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody CategoryDTO objDTO) {
        return super.update(id, objDTO);
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        return super.delete(id);
    }
}

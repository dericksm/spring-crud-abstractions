package com.derick.services;

import com.derick.entities.Category;
import com.derick.entities.dto.CategoryDTO;
import com.derick.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService<Category, CategoryDTO> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public JpaRepository<Category, Integer> getRepository() {
        return categoryRepository;
    }
}

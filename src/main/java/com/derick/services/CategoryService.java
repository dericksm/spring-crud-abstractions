package com.derick.services;

import com.derick.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService<Category> {

    public CategoryService(JpaRepository<Category, Integer> repository) {
        super(repository);
    }
}

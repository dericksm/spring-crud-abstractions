package com.derick.services;

import com.derick.entities.Category;
import com.derick.entities.Product;
import com.derick.entities.dto.CategoryDTO;
import com.derick.entities.dto.ProductDTO;
import com.derick.repositories.CategoryRepository;
import com.derick.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class ProductService extends AbstractService<Product, ProductDTO> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public JpaRepository<Product, Integer> getRepository() {
        return productRepository;
    }

    public Page<Product> findPage(String name, List<Integer> categoryList, Integer page, Integer size, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(direction), orderBy);
        List<Category> categories = categoryRepository.findAllById(categoryList);
        return productRepository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);
    }
}

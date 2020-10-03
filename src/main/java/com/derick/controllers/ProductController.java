package com.derick.controllers;//package com.derick.controllers;

import com.derick.controllers.utils.URLUtils;
import com.derick.entities.Product;
import com.derick.entities.dto.ProductDTO;
import com.derick.services.AbstractService;
import com.derick.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductController extends AbstractController<Product, ProductDTO>{

    @Autowired
    private ProductService productService;

    @Override
    public AbstractService<Product, ProductDTO> getService() {
        return productService;
    }

    @GetMapping(value = "/product-category")
    public ResponseEntity<Page<ProductDTO>> findPage(
            @RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "categories", defaultValue = "") String categories,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction
    ){
        List<Integer> categoriesList = URLUtils.decodeIntList(categories);
        Page<Product> objList = productService.findPage(URLUtils.decodeParam(search), categoriesList, page,size, orderBy, direction);
        Page<ProductDTO > dtoList = objList.map(obj -> getService().convertToOrderDto(obj));
        return ResponseEntity.ok().body(dtoList);

    }
}

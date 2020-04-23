package com.leivas.productservice.resource;

import com.leivas.productservice.model.ProductCategory;
import com.leivas.productservice.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class ProductCategoryResource {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategory> findAll() {

        return productCategoryService.findAllProductCategory();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> findById(@PathVariable String id) {

        return productCategoryService.findCategoryByIdOrThrow(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {

        productCategoryService.deleteCategoryById(id);
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createCategory(@RequestBody @Valid ProductCategory productCategory, HttpServletResponse response) {

        return productCategoryService.createNewCategory(productCategory, response);
    }


}

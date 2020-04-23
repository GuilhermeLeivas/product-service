package com.leivas.productservice.service;

import com.leivas.productservice.event.ResourceCreatedEvent;
import com.leivas.productservice.model.ProductCategory;
import com.leivas.productservice.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    public List<ProductCategory> findAllProductCategory() {

        return productCategoryRepository.findAll();
    }

    public ResponseEntity<ProductCategory> findCategoryByIdOrThrow(String id) {

        ProductCategory productCategory = productCategoryRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        return ResponseEntity.status(HttpStatus.OK).body(productCategory);
    }

    public void deleteCategoryById(String id) {

        productCategoryRepository.deleteById(id);
    }

    public ResponseEntity<ProductCategory> createNewCategory(ProductCategory productCategory, HttpServletResponse response) {

        ProductCategory savedProductCategory = productCategoryRepository.save(productCategory);

        publisher.publishEvent(new ResourceCreatedEvent(this, response, savedProductCategory.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProductCategory);
    }
}

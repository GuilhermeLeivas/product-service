package com.leivas.productservice.repository;

import com.leivas.productservice.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    public Page<Product> findByProductName(String productName, Pageable pageable);

    public Page<Product> findAll(Pageable pageable);

}

package com.leivas.productservice.service;

import com.leivas.productservice.event.ResourceCreatedEvent;
import com.leivas.productservice.model.Product;
import com.leivas.productservice.repository.ProductRepository;
import com.leivas.productservice.s3config.S3UploadLogic;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Service
public class ProdutoService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private S3UploadLogic s3;

    @Autowired
    private ApplicationEventPublisher publisher;


    public List<Product> findAllProductsOrSearchService(@Nullable String productName) {

        if(!productName.isEmpty()) {
            return productRepository.findByProductName(productName);
        } else {
            return productRepository.findAll();
        }
    }

    public ResponseEntity<Product> createNewProduct(Product product, HttpServletResponse response) {

        Product savedProduct = productRepository.save(product);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, product.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    public ResponseEntity<Product> findById(String id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException(1));

        return ResponseEntity.ok(product);
    }

    public void deleteProduct(String id) {

         productRepository.deleteById(id);
    }


    public void uploadPhoto(String id, MultipartFile file) {

        s3.uploadProductImage(id, file);
    }
}

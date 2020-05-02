package com.leivas.productservice.resource;

import com.leivas.productservice.model.Product;
import com.leivas.productservice.service.ProdutoService;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RestController
@RequestMapping("/api/v1/products")
public class ProductResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public Page<Product> findAllOrSearch(@RequestParam("productName") @Nullable String productName, Pageable pageable) {

        return produtoService.findAllProductsOrSearchService(productName, pageable);
    }
    
    @PostMapping(value = "/create")
    public ResponseEntity<Product> createNewProduct(@RequestBody @Valid Product product,
                                                    HttpServletResponse response) {

        Product newProduct = produtoService.createNewProduct(product, response);

        return ResponseEntity.status(CREATED).body(newProduct);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findProductById(@PathVariable String id) {

        Product productFound = produtoService.findById(id);

        return ResponseEntity.status(OK).body(productFound);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable String id) {

         produtoService.deleteProduct(id);
    }

    @PutMapping(value = "image-upload/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void productPictureUpload(@PathVariable String id, @RequestParam MultipartFile file) {

        produtoService.uploadPhoto(id, file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProdut(@RequestBody @Valid Product product, @PathVariable String id) {

        return produtoService.updateProduct(product, id);
    }

}

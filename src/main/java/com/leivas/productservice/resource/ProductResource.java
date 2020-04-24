package com.leivas.productservice.resource;

import com.leivas.productservice.model.Product;
import com.leivas.productservice.service.ProdutoService;
import com.sun.istack.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/products")
public class ProductResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Product> findAllOrSearch(@RequestParam("productName") @Nullable String productName) {

        return produtoService.findAllProductsOrSearchService(productName);
    }
    
    @PostMapping(value = "/create")
    public ResponseEntity<Product> createNewProduct(@RequestBody @Valid Product product,
                                                    HttpServletResponse response) {

        return produtoService.createNewProduct(product, response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findProductById(@PathVariable String id) {

        return produtoService.findById(id);
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

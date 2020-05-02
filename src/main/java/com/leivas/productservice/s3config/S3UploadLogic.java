package com.leivas.productservice.s3config;

import com.leivas.productservice.model.Product;
import com.leivas.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.MediaType.*;

@Service
public class S3UploadLogic {

    @Autowired
    private FileStore fileStore;

    @Autowired
    private ProductRepository productRepository;

    public void uploadProductImage(String ProductId, MultipartFile file) {

        // 1 - Check if the file is empty
        isFileEmpty(file);

        // 2 - Check if the file is an image
        isFileAnImage(file);

        // 3 - Check if the product exists in our database.
        Product product = checkIfProductExists(ProductId);

        // 4 - Grab some metadata from file if any
        Map<String, String> metadata = extractMetadata(file);

        // 5 - Store the image in S3
        String path = String.format("%s/%s", BucketName.PRODUCT_IMAGE.getBucketName(), ProductId);
        String fileName = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {
            fileStore.save(path, fileName, Optional.of(metadata), file.getInputStream());
            product.setPhotoPath(Optional.of(path + fileName));
            productRepository.save(product);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> extractMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private Product checkIfProductExists(String productId) {
        return productRepository.findById(productId)
                                .orElseThrow(() -> new EmptyResultDataAccessException(1));
    }

    private void isFileAnImage(MultipartFile file) {
        if(!Arrays.asList(IMAGE_PNG, IMAGE_GIF, IMAGE_JPEG)
                .contains(file.getContentType())) {
            throw new IllegalStateException("Cannot upload the file because its not an image");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()) {
            throw new IllegalStateException("Cannot upload the file");
        }
    }

}

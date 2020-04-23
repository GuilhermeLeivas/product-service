package com.leivas.productservice.s3config;

public enum BucketName {

    PRODUCT_IMAGE("pedro-esnaola-ecommerce-bucket");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}

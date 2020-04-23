package com.leivas.productservice.s3config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Bean
    public AmazonS3 s3() {

        AWSCredentials awsCredentials = new BasicAWSCredentials(
                "AKIAIOFZ4ISMBAWNLH3A",
                "8JIcnAN89/fvlIEgNZxYyGFGjsubDXx0a9x5DIdv");

        AmazonS3 build = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).withRegion(Regions.AP_SOUTH_1)
                .build();

        return build;

    }
}

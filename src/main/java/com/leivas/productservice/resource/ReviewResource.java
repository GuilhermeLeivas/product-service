package com.leivas.productservice.resource;

import com.leivas.productservice.model.Review;
import com.leivas.productservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewResource {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<Review> findReviewById(@PathVariable String id) {

        Review reviewById = reviewService.findReviewById(id);

        return ResponseEntity.status(OK).body(reviewById);
    }

    @PostMapping("/{productId}/{userId}")
    public ResponseEntity<Review> createNewReview(@RequestBody Review review, String productId, String userId, HttpServletResponse response) {

        Review newReview = reviewService.createNewReview(review, productId, userId, response);

        return ResponseEntity.status(CREATED).body(newReview);
    }
}

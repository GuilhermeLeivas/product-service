package com.leivas.productservice.resource;

import com.leivas.productservice.model.Review;
import com.leivas.productservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/reviews")
public class ReviewResource {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<Review> findReviewById(@PathVariable String id) {

        return reviewService.findReviewById(id);
    }

    @PostMapping("/{productId}/{userId}")
    public ResponseEntity<Review> createNewReview(@RequestBody Review review, String productId, String userId, HttpServletResponse response) {

        return reviewService.createNewReview(review, productId, userId, response);
    }
}

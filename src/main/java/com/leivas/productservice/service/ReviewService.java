package com.leivas.productservice.service;

import com.leivas.productservice.event.ResourceCreatedEvent;
import com.leivas.productservice.model.Product;
import com.leivas.productservice.model.Review;
import com.leivas.productservice.repository.ProductRepository;
import com.leivas.productservice.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    public ResponseEntity<Review> findReviewById(String id) {

        Optional<Review> reviewByIdFound = reviewRepository.findById(id);

        return ifReviewIsPresentOrNot(reviewByIdFound);

    }

    private ResponseEntity<Review> ifReviewIsPresentOrNot(Optional<Review> reviewByIdFound) {

        if(reviewByIdFound.isPresent()) {

            return ResponseEntity.ok(reviewByIdFound.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    public ResponseEntity<Review> createNewReview(Review review, String productId, String userId, HttpServletResponse response) {

        Optional<Product> product = checkIfProductExists(productId);
        review.setUserId(userId);

        reviewRepository.save(review);
        product.ifPresent(productFoundById -> productFoundById.addReview(review));
        publisher.publishEvent(new ResourceCreatedEvent(this, response, review.getId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    private Optional<Product> checkIfProductExists(String productId) {
        return productRepository.findById(productId);
    }
}

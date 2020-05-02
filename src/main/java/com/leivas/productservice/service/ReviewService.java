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

    public Review findReviewById(String id) {

        return reviewRepository.findById(id)
                                    .orElseThrow(() -> new EmptyResultDataAccessException(1));

    }
    public Review createNewReview(Review review, String productId, String userId, HttpServletResponse response) {

        Product product = checkIfProductExists(productId);
        review.setUserId(userId);

        reviewRepository.save(review);
        product.getReviews().add(review);
        productRepository.save(product);
        publisher.publishEvent(new ResourceCreatedEvent(this, response, review.getId()));

        return review;
    }

    public ResponseEntity<Review> updateReview(Review review, String reviewId, String userId) {

        // TODO : Implement this method/ i must verify if review userId its the same as the userId received inside the request

        return null;
    }

    private Product checkIfProductExists(String productId) {
        return productRepository.findById(productId).orElseThrow(() -> new EmptyResultDataAccessException(1));
    }
}

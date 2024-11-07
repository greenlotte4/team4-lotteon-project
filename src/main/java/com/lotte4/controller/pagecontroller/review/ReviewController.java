package com.lotte4.controller.pagecontroller.review;

import com.lotte4.dto.mongodb.ReviewDTO;
import com.lotte4.service.mongodb.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review")
    public ResponseEntity<List<ReviewDTO>> findAllReviews() {

        List<ReviewDTO> reviewDocuments = reviewService.findAllReview();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewDocuments);
    }

    @GetMapping("/review/{prodId}")
    public ResponseEntity<Map<String, Object>> findReviewsByProdId(
            @PathVariable int prodId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewDTO> reviewDocuments = reviewService.findReviewByProdId(prodId, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("content", reviewDocuments.getContent());
        response.put("currentPage", reviewDocuments.getNumber());
        response.put("totalPages", reviewDocuments.getTotalPages());
        response.put("totalItems", reviewDocuments.getTotalElements());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/review")
    public ResponseEntity<ReviewDTO> insertReview(ReviewDTO review1) {

        ReviewDTO savedUser1 = reviewService.insertReview(review1);

        return ResponseEntity
                .ok()
                .body(savedUser1);
    }

    @DeleteMapping("/reviews/{uid}")
    public ResponseEntity<Boolean> deleteReview(@PathVariable("uid") String uid) {

        boolean result = reviewService.deleteReview(uid);

        return ResponseEntity
                .ok()
                .body(result);
    }


}

package com.lotte4.controller.pagecontroller.review;

import com.lotte4.document.ReviewDocument;
import com.lotte4.dto.ReviewDTO;
import com.lotte4.service.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;
    @GetMapping("/review")
    public ResponseEntity<List<ReviewDTO>> findAllReviews() {

        List<ReviewDTO> reviewDocuments = reviewService.findAllReview();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviewDocuments);
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

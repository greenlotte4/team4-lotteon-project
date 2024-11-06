package com.lotte4.service.review;

import com.lotte4.document.ReviewDocument;

import com.lotte4.dto.ProductVariantsDTO;
import com.lotte4.dto.ReviewDTO;
import com.lotte4.entity.ProductVariants;
import com.lotte4.repository.ProductVariantsRepository;
import com.lotte4.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ModelMapper modelMapper;

    private final ProductVariantsRepository productVariantsRepository;

    private final ReviewRepository reviewRepository;

    // 모든 리뷰 조회
    public List<ReviewDTO> findAllReview() {
        List<ReviewDocument> reviewList = reviewRepository.findAll();

        // Document 리스트를 DTO 리스트로 변환
        return reviewList.stream()
                .map(review -> modelMapper.map(review, ReviewDTO.class))
                .collect(Collectors.toList());
    }


    public Page<ReviewDTO> findReviewByProdId(int prodId, Pageable pageable) {
        Page<ReviewDocument> reviewPage = reviewRepository.findByProdId(prodId, pageable);
        return reviewPage.map(this::convertToReviewDTO);
    }

    public Page<ReviewDTO> findReviewsByUid(String uid, Pageable pageable) {
        Page<ReviewDocument> reviewPage = reviewRepository.findByUid(uid, pageable);
        return reviewPage.map(this::convertToReviewDTO);
    }

    // 공통 변환 로직을 메서드로 분리
    private ReviewDTO convertToReviewDTO(ReviewDocument review) {

        ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
        int variantId = review.getVariantId();
        Optional<ProductVariants> optionalProductVariants = productVariantsRepository.findById(variantId);
        optionalProductVariants.ifPresent(reviewDTO::setProductVariants);

        return reviewDTO;
    }





    // 특정 리뷰 조회
    public ReviewDTO findReview(String uid) {
        Optional<ReviewDocument> optReview = reviewRepository.findByUid(uid);

        // Optional이 존재할 경우 Document를 DTO로 변환하여 반환
        return optReview.map(review -> modelMapper.map(review, ReviewDTO.class)).orElse(null);
    }

    // 리뷰 추가
    public ReviewDTO insertReview(ReviewDTO reviewDTO) {


        ReviewDocument reviewDocument = modelMapper.map(reviewDTO, ReviewDocument.class);
        ReviewDocument savedReview = reviewRepository.save(reviewDocument);

        return modelMapper.map(savedReview, ReviewDTO.class);
    }

    // 리뷰 업데이트
    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        Optional<ReviewDocument> optReview = reviewRepository.findByUid(reviewDTO.getUid());

        if (optReview.isPresent()) {
            ReviewDocument savedReview = optReview.get();

            // 필요한 필드 업데이트
            savedReview.setContent(reviewDTO.getContent());
            savedReview.setRegDate(reviewDTO.getRegDate());

            ReviewDocument modifiedReview = reviewRepository.save(savedReview);

            // 수정된 Document를 DTO로 변환하여 반환
            return modelMapper.map(modifiedReview, ReviewDTO.class);
        }

        return null;
    }

    // 리뷰 삭제
    public boolean deleteReview(String uid) {
        Optional<ReviewDocument> optReview = reviewRepository.findByUid(uid);

        if (optReview.isPresent()) {
            reviewRepository.delete(optReview.get());
            return true;
        }

        return false;
    }
}

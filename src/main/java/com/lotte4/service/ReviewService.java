package com.lotte4.service;


import com.lotte4.dto.ReviewDTO;
import com.lotte4.entity.Review;
import com.lotte4.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;

    public List<ReviewDTO> findAllReviews() {

        List<Review> reviewList = reviewRepository.findAll();
        List<ReviewDTO> reviewDTOList = new ArrayList<>();

        for (Review review : reviewList) {
            ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
            reviewDTO.setRegDateSub(review.getRegDate().toString().substring(0,10));
            reviewDTOList.add(reviewDTO);
        }
        return reviewDTOList;
    }

}

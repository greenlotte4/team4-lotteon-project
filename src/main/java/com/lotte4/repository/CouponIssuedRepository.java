package com.lotte4.repository;

import com.lotte4.entity.CouponIssued;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssuedRepository extends JpaRepository<CouponIssued, Integer> {
    boolean existsByCoupon_couponIdAndUser_uid(int couponId,String uid);
}
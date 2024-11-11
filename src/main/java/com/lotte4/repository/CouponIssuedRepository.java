package com.lotte4.repository;

import com.lotte4.entity.CouponIssued;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CouponIssuedRepository extends JpaRepository<CouponIssued, Integer> {
    boolean existsByCoupon_couponIdAndUser_uid(int couponId,String uid);
    List<CouponIssued> findByUser_uid(String uid);
    @Query("SELECT ci FROM CouponIssued ci JOIN ci.user u JOIN ci.coupon c WHERE u.userId = :uid")
    List<CouponIssued> findByUserUid(@Param("uid") int uid);
}
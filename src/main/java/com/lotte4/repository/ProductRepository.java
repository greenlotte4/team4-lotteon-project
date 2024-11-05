package com.lotte4.repository;

import com.lotte4.entity.Product;
import com.lotte4.entity.ProductCate;
import com.lotte4.entity.SellerInfo;
import com.lotte4.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductCateId(ProductCate productCateId);
    List<Product> findBySellerInfoId(SellerInfo sellerInfoId);

    // status 가 0 인 회원 목록 select(관리자 상품목록을 위함)
    Page<Product> findByStatus(int status, Pageable pageable);

    // name 검색
    Page<Product> findByStatusAndNameContaining(int status, String name, int sellerInfoId, Pageable pageable);

    // productId 검색
    Page<Product> findByStatusAndProductIdContaining(int status, int productId, int sellerInfoId, Pageable pageable);

    // sellerInfoId 검색
    Page<Product> findByStatusAndSellerInfoIdContaining(int status, int sellerInfoId, Pageable pageable);

    // company 검색
    Page<Product> findByStatusAndCompanyContaining(int status, String company, int sellerInfoId, Pageable pageable);


}
package com.lotte4.repository;

import com.lotte4.entity.Product;
import com.lotte4.entity.ProductCate;
import com.lotte4.entity.User;
import com.lotte4.repository.custom.ProductRepositoyCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, ProductRepositoyCustom {
    List<Product> findByProductCateId(ProductCate productCateId);

//    // status가  0 인 상품 목록 select
//    Page<Product> findByStatus(int status, Pageable pageable);
//
//    // status가 0 이고, keyword 검색하는 메서드 (모든 필드에서 검색)
//    Page<Product> findByStatusAndSearchCategoryAndKeyword(
//            int status, String searchCategory, String keyword, Pageable pageable);

}

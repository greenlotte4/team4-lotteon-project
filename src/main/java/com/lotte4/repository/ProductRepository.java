package com.lotte4.repository;

import com.lotte4.entity.Product;
import com.lotte4.entity.ProductCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductCateId(ProductCate productCateId);
}

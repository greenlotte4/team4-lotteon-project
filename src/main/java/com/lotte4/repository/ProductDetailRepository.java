package com.lotte4.repository;

import com.lotte4.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {
    public ProductDetail findByProductId(Integer productId);
    public void deleteByProductId(Integer productId);

}

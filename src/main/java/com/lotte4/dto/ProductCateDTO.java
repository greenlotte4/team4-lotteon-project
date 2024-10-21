package com.lotte4.dto;

import com.lotte4.entity.ProductCate;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCateDTO {
    private int productCateId;

    // 이름
    private String name;

    private List<ProductCateDTO> children = new ArrayList<>();

    public ProductCateDTO(ProductCate productCate) {
        this.productCateId = productCate.getProductCateId();
        this.name = productCate.getName();
        setChildren(productCate.getChildren());
    }

    public ProductCateDTO(int productCateId, String name) {
        this.productCateId = productCateId;
        this.name = name;
    }

    private void setChildren(List<ProductCate> children) {
        children.forEach(productCate -> {
            ProductCateDTO childDTO = new ProductCateDTO(productCate);
            this.children.add(childDTO);
        });
    }
}

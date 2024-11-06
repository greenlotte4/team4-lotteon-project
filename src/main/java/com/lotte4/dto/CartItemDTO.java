package com.lotte4.dto;

import com.lotte4.entity.Product;
import com.lotte4.entity.ProductVariants;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDTO {
    private Integer variantId;
    private String sku;
    private String productName;
    private Integer price;
    private Integer count;
    private Integer stock;
    private String img;
    private Integer deliveryFee;
    private ProductVariantsDTO ProductVariants;
    private Product product;

    private void getPrductId (){
        this.ProductVariants.getProduct().getProductId();
    }

}

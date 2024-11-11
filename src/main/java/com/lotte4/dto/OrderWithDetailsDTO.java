package com.lotte4.dto;

import com.lotte4.entity.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderWithDetailsDTO {

    private Order order;
    private OrderItems orderItem;
    private MemberInfo memberInfo;
    private ProductVariants productVariant;
    private Product product;

}

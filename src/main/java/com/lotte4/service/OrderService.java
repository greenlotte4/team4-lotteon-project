package com.lotte4.service;

import com.lotte4.dto.OrderDTO;
import com.lotte4.dto.ProductDTO;
import com.lotte4.entity.MemberInfo;
import com.lotte4.entity.Order;
import com.lotte4.entity.Product;
import com.lotte4.repository.OrderRepository;
import com.lotte4.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {


    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    //상품 단품 구매 건(조회부분)
    public ProductDTO selectByProduct(int productId) {
        return productRepository.findById(productId)
            .map(product -> {
                ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
                log.info("Selected product: " + productDTO);
                return productDTO;
            })
            .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + productId));
    }


    //상품 cart 담았을 때(조회부분)
    public ProductDTO selectByCart() {
        return modelMapper.map(orderRepository.findAll(), ProductDTO.class);
    }


    public Order insertOrder(OrderDTO orderDTO){
        Order order = modelMapper.map(orderDTO, Order.class);

        if (orderDTO.getProductId() != 0) {
            Product product = new Product();
            product.setProductId(orderDTO.getProductId());
            order.setProduct(product);
        } else {
            throw new IllegalArgumentException("상품정보 없음");
        }

        if (orderDTO.getMemberInfoId() != 0) {
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setMemberInfoId(orderDTO.getMemberInfoId());
            order.setMemberInfo(memberInfo);
        } else {
            throw new IllegalArgumentException("사용자 정보 없음");
        }

        return orderRepository.save(order);
    }

}

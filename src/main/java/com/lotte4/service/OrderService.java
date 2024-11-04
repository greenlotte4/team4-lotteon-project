package com.lotte4.service;

import com.lotte4.dto.*;
import com.lotte4.entity.*;
import com.lotte4.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/*
     날짜 : 2024/10/30
     이름 : 조수빈
     내용 : orderService 생성




     추후 작업내용 11.04 이후 업데이트 하는 메서드 추가(내부에서 돌릴것인가? or 백단에서 돌릴것인가?)
*/

@Log4j2
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {


    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final DeliveryRepository deliveryRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final OrderItemsRepository orderItemsRepository;


    public List<OrderDTO> getOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (Order order : orders) {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }


    //상품 단품 구매 건(조회부분)
    public CartDTO selectByProductAsCartDTO(int variantId, int count) {
        // 상품 변형 조회
        Optional<ProductVariants> productVariantsOptional = productVariantsRepository.findById(variantId);

        if (productVariantsOptional.isPresent()) {
            ProductVariants productVariants = productVariantsOptional.get();

            // ProductVariantsDTO 생성
            ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();
            productVariantsDTO.setVariant_id(productVariants.getVariant_id());
            productVariantsDTO.setSku(productVariants.getSku());
            productVariantsDTO.setPrice(productVariants.getPrice());
            productVariantsDTO.setStock(productVariants.getStock());
            productVariantsDTO.setOptions(productVariants.getOptions());
            productVariantsDTO.setCreated_at(productVariants.getCreated_at());
            productVariantsDTO.setUpdated_at(productVariants.getUpdated_at());

            // Product_V_DTO 생성 및 ProductVariantsDTO에 설정
            Product_V_DTO product_V_DTO = new Product_V_DTO();
            product_V_DTO.setProductId(productVariants.getProduct().getProductId());
            product_V_DTO.setName(productVariants.getProduct().getName());
            product_V_DTO.setDescription(productVariants.getProduct().getDescription());
            product_V_DTO.setCompany(productVariants.getProduct().getCompany());
            product_V_DTO.setPrice(productVariants.getProduct().getPrice());
            product_V_DTO.setDiscount(productVariants.getProduct().getDiscount());
            product_V_DTO.setPoint(productVariants.getProduct().getPoint());
            product_V_DTO.setSold(productVariants.getProduct().getSold());
            product_V_DTO.setDeliveryFee(productVariants.getProduct().getDeliveryFee());
            product_V_DTO.setHit(productVariants.getProduct().getHit());
            product_V_DTO.setReview(productVariants.getProduct().getReview());
            product_V_DTO.setImg1(productVariants.getProduct().getImg1());
            product_V_DTO.setImg2(productVariants.getProduct().getImg2());
            product_V_DTO.setImg3(productVariants.getProduct().getImg3());
            product_V_DTO.setDetail(productVariants.getProduct().getDetail());
            productVariantsDTO.setProduct(ProductDTO.builder().build());

            // CartDTO 생성 및 설정
            CartDTO cartDTO = new CartDTO();
            cartDTO.setProductVariants(productVariants);
            cartDTO.setCount(count); // 단품 구매 시 수량 설정

            log.info("CartDTO for single product purchase: " + cartDTO);
            return cartDTO;
        } else {
            log.warn("옵션아이디 " + variantId + " 찾을 수 없음.");
            return null;
        }
    }



    // 2024-11-04 수정 완료
    public void insertOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setPayment(orderDTO.getPayment());
        order.setStatus(orderDTO.getStatus());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setRecipZip(orderDTO.getRecipZip());
        order.setRecipAddr1(orderDTO.getRecipAddr1());
        order.setRecipAddr2(orderDTO.getRecipAddr2());
        order.setRecipHp(orderDTO.getRecipHp());
        order.setRecipName(orderDTO.getRecipName());
        order.setUsePoint(orderDTO.getUsePoint());
        order.setBuyDate(orderDTO.getBuyDate());


        if (orderDTO.getMemberInfo() != null) {
            int memberInfoId = orderDTO.getMemberInfo().getMemberInfoId();
            Optional<MemberInfo> memberInfoOptional = memberInfoRepository.findById(memberInfoId);
            memberInfoOptional.ifPresent(order::setMemberInfo);
        }

        if (orderDTO.getOrderItems() != null) {
            for (OrderItemsDTO orderItemsDTO : orderDTO.getOrderItems()) {
                OrderItems orderItems = new OrderItems();
                orderItems.setCount(orderItemsDTO.getCount());
                orderItems.setDeliveryFee(orderItemsDTO.getDeliveryFee());
                orderItems.setOriginDiscount(orderItemsDTO.getOriginDiscount());
                orderItems.setOriginPoint(orderItemsDTO.getOriginPoint());
                orderItems.setOriginPrice(orderItemsDTO.getOriginPrice());
                orderItems.setItemOption(orderItemsDTO.getProductVariants().getSku());
                orderItems.setVariantId(orderItemsDTO.getVariantId());

                // ProductVariants 설정
                if (orderItemsDTO.getProductVariants() != null) {
                    int variantId = orderItemsDTO.getProductVariants().getVariant_id();
                    Optional<ProductVariants> productVariantsOptional = productVariantsRepository.findById(variantId);

                    productVariantsOptional.ifPresent(productVariants -> {
                        ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();
                        productVariantsDTO.setVariant_id(productVariants.getVariant_id());
                        productVariantsDTO.setSku(productVariants.getSku());
                        productVariantsDTO.setPrice(productVariants.getPrice());
                        productVariantsDTO.setStock(productVariants.getStock());
                        productVariantsDTO.setOptions(productVariants.getOptions());
                        productVariantsDTO.setCreated_at(productVariants.getCreated_at());
                        productVariantsDTO.setUpdated_at(productVariants.getUpdated_at());
                        orderItems.setItemOption(productVariantsDTO.getSku());
                    });
                }

                // OrderItems를 Order에 추가
                order.addOrderItem(orderItems);

                log.info("OrderDTO content before setting in Delivery: " + orderDTO.getContent());
                // Delivery 생성 및 설정
                Delivery delivery = new Delivery();
                delivery.setOrderItem(orderItems);
                delivery.setDeliveryDate(LocalDateTime.now().plusDays(3));
                delivery.setContent(orderDTO.getContent());
                log.info("16722757257"+delivery.toString());
                deliveryRepository.save(delivery);
            }
        }

        // 최종 Order 저장
        orderRepository.save(order);
        log.info("주문저장 로그 " + order);
    }




    //각 repository 가져와서 업데이트 하기
    public Optional<OrderDTO> updateOrder(List<OrderDTO> orderDTOList) {
        return null;
    }


    public List<OrderDTO> selectAllOrders(){
        List<Order> orders = orderRepository.findAll();
        log.info("selectAllOrders = " + orders);
        List<OrderDTO> orderDTOS = new ArrayList<>();

        for (Order order : orders) {
            OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);

            log.info("Selected order: " + order);
            // ProductVariants 처리 11월 04일 이후 아이디는 principal에서 받아와야함
            if (orderDTO.getProductVariants() == null) {
                ProductVariants productVariants = ProductVariants.builder()
                        .variant_id(1)
                        .build();
                orderDTO.setProductVariants(ProductVariantsDTO.builder().build());
            } else {
                orderDTO.setProductVariants(orderDTO.getProductVariants());
            }

            orderDTO.setMemberInfo(order.getMemberInfo().toDTO());

            if (order.getProductVariants() != null && order.getProductVariants().getProduct() != null) {
                ProductDTO productDTO = modelMapper.map(order.getProductVariants().getProduct(), ProductDTO.class);
                log.info("ProductDTO: " + productDTO);
            } else {
                log.warn("Product is null for order ID: " + order.getOrderId());
            }

            orderDTOS.add(orderDTO);
        }

        log.info("select ALL Orders: " + orderDTOS);
        return orderDTOS;
    }

    public List<UserDTO> selectAllUser(){
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            userDTOS.add(userDTO);
        }
        log.info("select ALL User: " + userDTOS);
        return userDTOS;
    }


}

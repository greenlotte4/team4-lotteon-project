package com.lotte4.service;

import com.lotte4.dto.*;
import com.lotte4.entity.*;
import com.lotte4.repository.*;
import com.lotte4.repository.admin.config.CouponRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
    private final PointRepository pointRepository;
    private final CouponRepository couponRepository;


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
    public OrderDirectBuyDTO getProduct(CartResponseDTO cartResponseDTO) {
        OrderDirectBuyDTO orderDirectBuyDTO = new OrderDirectBuyDTO();

        List<Integer> ids = cartResponseDTO.getProductVariants();
        List<Integer> counts = cartResponseDTO.getCounts();

        List<ProductVariants> productVariantsList = productVariantsRepository.findByVariantIdIn(ids);

        Map<ProductVariants, Integer> variantCountMap = new HashMap<>();
        for (int i = 0; i < productVariantsList.size(); i++) {
            ProductVariants productVariants = productVariantsList.get(i);
            int count = counts.get(i);
            variantCountMap.put(productVariants, count);
        }

        orderDirectBuyDTO.setVariantCountMap(variantCountMap);

        log.info("orderDirectBuyDTO: " + orderDirectBuyDTO);
        return orderDirectBuyDTO;
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
                ProductDTO productDTO =new ProductDTO(order.getProductVariants().getProduct());
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




    //포인트값 넣는 곳
    public PointDTO selectUserPoint(int uid){
        Point points = pointRepository.findById(uid).get();
        log.info("selectUserPoint uid: " + points);
        return modelMapper.map(points, PointDTO.class);

    }


    //사용자 납품된 쿠폰 값
    public CouponDTO selectUserCoupon(int uid){
        Optional<Coupon> coupon = couponRepository.findById(uid);
        log.info("selectUserCoupon uid: " + coupon);
        return modelMapper.map(coupon.get(), CouponDTO.class);
    }

    public OrderDTO selectRecentOrder(MemberInfo memberInfo) {
        Order recentOrder = orderRepository.findFirstByMemberInfoOrderByBuyDateDesc(memberInfo);
        return new OrderDTO(recentOrder);
    }

    // orderItems 의 variantId를 받아와 조회하고 DTO로 변환 후 리스트에 담아 반환
    public List<OrderItemsDTO> selectProductCompany(List<OrderItemsDTO> orderItems) {

        for (OrderItemsDTO orderItem : orderItems) {
            Optional<ProductVariants> optional = productVariantsRepository.findById(orderItem.getVariantId());
            if (optional.isPresent()) {
                ProductVariants productVariants = optional.get();
                orderItem.setProductVariants(new ProductVariantsDTO(productVariants));
            }
        }
        return orderItems;
    }


}

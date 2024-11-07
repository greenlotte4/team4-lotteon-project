package com.lotte4.service.admin.config;

import com.lotte4.dto.CouponDTO;
import com.lotte4.dto.UserDTO;
import com.lotte4.entity.Coupon;
import com.lotte4.entity.User;
import com.lotte4.repository.admin.config.CouponRepository;
import com.lotte4.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class CouponService {

    private final ModelMapper modelMapper;
    private final CouponRepository couponRepository;
    private final UserService userService;

    public List<CouponDTO> getAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        List<CouponDTO> couponDTOS = new ArrayList<>();

        for(Coupon coupon : coupons){
            CouponDTO couponDTO = modelMapper.map(coupon, CouponDTO.class);
            couponDTO.setIDate(couponDTO.getIDate().substring(0,10));
            couponDTO = changeBenefit(coupon, couponDTO);
            couponDTOS.add(couponDTO);
        }
        return couponDTOS;
    }

    public void insertCoupon(CouponDTO couponDTO) {

        //"임시" 유저지정 삭제필요!!!!
        UserDTO user = userService.selectUser("ekkang2");

        log.info(user.toString());

        couponDTO.setUsers(user);

        log.info(couponDTO);

        Coupon coupon = modelMapper.map(couponDTO, Coupon.class);

        log.info(coupon);
        couponRepository.save(coupon);
    }


    //시간날때 enum으로 변경하기
    public CouponDTO changeBenefit(Coupon coupon, CouponDTO couponDTO) {
        int benefit = coupon.getBenefit();

        switch (benefit) {
            case 1:
                couponDTO.setBenefit("1,000원 할인");
                break;
            case 2:
                couponDTO.setBenefit("2,000원 할인");
                break;
            case 3:
                couponDTO.setBenefit("3,000원 할인");
                break;
            case 4:
                couponDTO.setBenefit("4,000원 할인");
                break;
            case 5:
                couponDTO.setBenefit("5,000원 할인");
                break;
            case 6:
                couponDTO.setBenefit("10% 할인");
                break;
            case 7:
                couponDTO.setBenefit("20% 할인");
                break;
            case 8:
                couponDTO.setBenefit("30% 할인");
                break;
            case 9:
                couponDTO.setBenefit("40% 할인");
                break;
            case 10:
                couponDTO.setBenefit("50% 할인");
                break;
            case 0:
                couponDTO.setBenefit("배송비 무료");
                break;
        }

        return couponDTO;
    }

    public List<CouponDTO> getCouponsByMemberInfoId(int memberInfoId) {
        return couponRepository.findCouponsByMemberInfoId(memberInfoId);
    }
}

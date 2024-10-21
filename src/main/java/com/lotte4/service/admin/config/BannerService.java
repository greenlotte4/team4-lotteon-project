package com.lotte4.service.admin.config;

import com.lotte4.dto.BannerDTO;
import com.lotte4.entity.Banner;
import com.lotte4.repository.admin.config.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BannerService {
    private final ModelMapper modelMapper;
    private final BannerRepository bannerRepository;

    public BannerDTO getBanner(){
        List<Banner> bannerList = bannerRepository.findAll();
        return null;
    }



}

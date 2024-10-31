package com.lotte4.service.admin.config;

import com.lotte4.dto.BannerDTO;
import com.lotte4.entity.Banner;
import com.lotte4.repository.admin.config.BannerRepository;
import com.lotte4.service.CachingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class BannerService {
    private final ModelMapper modelMapper;
    private final BannerRepository bannerRepository;
    private final CachingService cachingService;

    public List<BannerDTO> getAllBanners(){
        List<Banner> banners = bannerRepository.findAll();
        List<BannerDTO> bannerDTOs = new ArrayList<>();

        for(Banner banner : banners){
            bannerDTOs.add(modelMapper.map(banner, BannerDTO.class));
        }
        return bannerDTOs;
    }

    public List<BannerDTO> getBannersByLocation(String location){
        List<Banner> banners = bannerRepository.findByLocation(location);
        List<BannerDTO> bannerDTOs = new ArrayList<>();

        for(Banner banner : banners){
            bannerDTOs.add(modelMapper.map(banner, BannerDTO.class));
        }
        return bannerDTOs;
    }




    public List<BannerDTO> getAllBannersWithLocation(){
        Random random = new Random();
        List<List<BannerDTO>> findResult = cachingService.getAllBannersWithLocationPre();

        //메인 슬라이드 전부 넣기
        List<BannerDTO> bannerDTOs = findResult.get(0);
        //랜덤하게 섞기
        Collections.shuffle(bannerDTOs);

        //랜덤으로 하나 정해서 리스트에 삽입
        for (int count = 1; count < findResult.size(); count++) {
            List<BannerDTO> bannerDTOList = findResult.get(count);
            if (!bannerDTOList.isEmpty()){
                bannerDTOs.add(bannerDTOList.get(random.nextInt(bannerDTOList.size())));
            }
        }
        return bannerDTOs;
    }


    public String insertBanner(BannerDTO bannerDTO, MultipartFile bannerImg) throws IOException {

        BannerDTO resultBannerDTO = cachingService.insertBanner(bannerDTO, bannerImg);

        if(resultBannerDTO.getName() == null){
            return "fail";
        }
        return "success";
    }

    public void deleteBanner(int bannerId){
        cachingService.deleteBanner(bannerId);
    }
}

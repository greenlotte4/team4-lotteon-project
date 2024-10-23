package com.lotte4.service.admin.config;

import com.lotte4.dto.BannerDTO;
import com.lotte4.entity.Banner;
import com.lotte4.repository.admin.config.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class BannerService {
    private final ModelMapper modelMapper;
    private final BannerRepository bannerRepository;

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

    public String insertBanner(BannerDTO bannerDTO, MultipartFile bannerImg) throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            if (!uploadDirectory.mkdirs()) {
                throw new IOException("디렉토리를 생성할 수 없습니다: " + uploadDir);
            }
        }

        if (!bannerImg.isEmpty()) {
            String bannerImgFileName = "banner_" + bannerDTO.getName() + "_" + bannerDTO.getLocation() + "_" + System.currentTimeMillis() + ".png";
            bannerImg.transferTo(new File(uploadDir + bannerImgFileName));
            bannerDTO.setImg(bannerImgFileName);
        }


        Banner banner = modelMapper.map(bannerDTO, Banner.class);
        Banner resultBanner = bannerRepository.save(banner);
        if(resultBanner.getName() == null){
            return "fail";
        }
        return "success";
    }
}

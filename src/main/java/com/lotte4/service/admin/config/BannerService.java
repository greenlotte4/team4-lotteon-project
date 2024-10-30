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

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class BannerService {
    private final ModelMapper modelMapper;
    private final BannerRepository bannerRepository;

    public BannerDTO getBanner(int bannerId) {
        Optional<Banner> banner = bannerRepository.findById(bannerId);
        return banner.map(value -> modelMapper.map(value, BannerDTO.class)).orElse(null);
    }

    public List<BannerDTO> getAllBanners(){
        List<Banner> banners = bannerRepository.findAll();
        List<BannerDTO> bannerDTOs = new ArrayList<>();

        for(Banner banner : banners){
            if(banner.getState() == 1){
                bannerDTOs.add(modelMapper.map(banner, BannerDTO.class));
            }
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
        //MAIN2는 전부 삽입
        List<Banner> banners = bannerRepository.findByLocationAndState("MAIN2",1);
        // 슬라이드 배너 랜덤화
        if(!banners.isEmpty()){
            Collections.shuffle(banners);
        }
        else{
            //활성화된 배너가 없을 시
            Banner banner = new Banner();
            banner.setLocation("MAIN2");
            banner.setState(1);
            banner.setImg("default_MAIN2.png");
            banner.setName("default_MAIN2_Banner");
            banner.setBackground("#FFFFFF");
            banners.add(banner);
        }


        String[] bannerLocationList = {"MAIN1","PRODUCT1","MY1","MEMBER1"};
        for(String location : bannerLocationList){
            List<Banner> bannerTMP = bannerRepository.findByLocationAndState(location,1);
            if (!bannerTMP.isEmpty()){
                banners.add(bannerTMP.get(random.nextInt(bannerTMP.size())));
            }
            else{
                //활성화된 배너가 없을 시
                Banner banner = new Banner();
                banner.setLocation(location);
                banner.setState(1);
                banner.setImg("default_"+location+".png");
                banner.setName("default_"+location+"_Banner");
                banner.setBackground("#FFFFFF");
                banners.add(banner);
            }
        }
        List<BannerDTO> bannerDTOs = new ArrayList<>();
        for(Banner banner : banners){
            bannerDTOs.add(modelMapper.map(banner, BannerDTO.class));
        }
        return bannerDTOs;
    }

    public String insertBanner(BannerDTO bannerDTO, MultipartFile bannerImg) throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/config/";

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

        //활성화
        bannerDTO.setState(1);

        Banner banner = modelMapper.map(bannerDTO, Banner.class);
        Banner resultBanner = bannerRepository.save(banner);
        if(resultBanner.getName() == null){
            return "fail";
        }
        return "success";
    }

    public void deleteBanner(int bannerId){
        bannerRepository.deleteById(bannerId);
    }

    public void updateBannerState(BannerDTO bannerDTO, int state){
        Optional<Banner> bannerOpt = bannerRepository.findById(bannerDTO.getBannerId());
        if(bannerOpt.isPresent()){
            Banner banner = bannerOpt.get();
            banner.setState(state);
            bannerRepository.save(banner);
        }
    }

    public boolean expireBannerCheck(BannerDTO bannerDTO){
        Optional<Banner> bannerOpt = bannerRepository.findById(bannerDTO.getBannerId());
        if(bannerOpt.isPresent()) {
            Banner banner = bannerOpt.get();
            Date eDate = banner.getEDate();
            String time = banner.getETime();

            //LocalDateTime으로 변환
            LocalDateTime eDay = new java.sql.Timestamp(eDate.getTime()).toLocalDateTime();
            eDay = eDay.plusHours(Long.parseLong(time.substring(0, 2)));
            eDay = eDay.plusMinutes(Long.parseLong(time.substring(3, 5)));
            //현재 시간과 비교
            boolean expire = eDay.isBefore(LocalDateTime.now());
            return expire;
        }
        return false;
    }
}

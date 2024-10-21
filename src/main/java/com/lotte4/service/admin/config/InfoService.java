package com.lotte4.service.admin.config;

import com.lotte4.dto.admin.config.InfoDTO;
import com.lotte4.entity.Info;
import com.lotte4.repository.admin.config.InfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Log4j2
public class InfoService {

    private final InfoRepository infoRepository;
    private final ModelMapper modelMapper;

    public Info selectInfo() {
        return infoRepository.findById(1).orElse(new Info());
    }
    public InfoDTO selectInfoDTO() {
        return infoRepository.findById(1)
                .map(info -> modelMapper.map(info, InfoDTO.class))
                .orElse(null);
    }
    @Transactional
    public InfoDTO updateInfoTitleAndSubtitle(InfoDTO infoDTO) {
        int resultRow = infoRepository.updateTitleAndSubtitle(infoDTO.getTitle(), infoDTO.getSubTitle());
        // 이 값이 1일 때 성공적으로 수정된 것을 의미
        if(resultRow ==1){
            return infoDTO;
        }else
            return null;
    }

    @Transactional
    public InfoDTO updateCompanyInfo(InfoDTO infoDto) {
        int resultRow = infoRepository.updateCompanyInfo(
                infoDto.getCompanyName(),
                infoDto.getCompanyCeo(),
                infoDto.getCompanyBusinessNumber(),
                infoDto.getMosaNumber(),
                infoDto.getCompanyAddress(),
                infoDto.getCompanyAddress2()
        );
        if(resultRow ==1){
          return infoDto;
        }else
            return null;
    }

    @Transactional
    public InfoDTO updateCompanyCs(InfoDTO infoDto) {
        int resultRow = infoRepository.updateCompanyCs(
                infoDto.getCsHp(),
                infoDto.getCsWorkingHours(),
                infoDto.getCsEmail(),
                infoDto.getConsumer()
        );
        if(resultRow ==1){
            return infoDto;
        }else
            return null;
    }

    @Transactional
    public InfoDTO updateCopyright(InfoDTO infoDto) {
        int resultRow = infoRepository.updateCopyright(
                infoDto.getCopyright()
        );
        if(resultRow ==1){
            return infoDto;
        }else
            return null;
    }
    public InfoDTO uploadLogos(MultipartFile headerLogo, MultipartFile footerLogo, MultipartFile favicon) throws IOException {
        Info info = infoRepository.findById(1).orElseThrow();
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            if (!uploadDirectory.mkdirs()) {
                throw new IOException("디렉토리를 생성할 수 없습니다: " + uploadDir);
            }
        }

        if (!headerLogo.isEmpty()) {
            String headerLogoFileName = "headerLogo_" + System.currentTimeMillis() + ".png";
            headerLogo.transferTo(new File(uploadDir + headerLogoFileName));
            info.setHeaderLogo(headerLogoFileName);
        }

        if (!footerLogo.isEmpty()) {
            String footerLogoFileName = "footerLogo_" + System.currentTimeMillis() + ".png";
            footerLogo.transferTo(new File(uploadDir + footerLogoFileName));
            info.setFooterLogo(footerLogoFileName);
        }

        if (!favicon.isEmpty()) {
            String faviconFileName = "favicon_" + System.currentTimeMillis() + ".ico";
            favicon.transferTo(new File(uploadDir + faviconFileName));
            info.setFavicon(faviconFileName); // 파일명 저장
        }

        // Info 엔티티 저장
        return modelMapper.map(infoRepository.save(info), InfoDTO.class);
    }
}


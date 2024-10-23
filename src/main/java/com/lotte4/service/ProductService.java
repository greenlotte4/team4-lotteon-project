package com.lotte4.service;

import com.lotte4.dto.CateForProdRegisterDTO;
import com.lotte4.dto.ProductDTO;
import com.lotte4.dto.ProductDetailDTO;
import com.lotte4.entity.*;
import com.lotte4.repository.CategoryRepository;
import com.lotte4.repository.ProductDetailRepository;
import com.lotte4.repository.ProductRepository;
import com.lotte4.repository.ProductVariantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public void insertProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        productRepository.save(product);
    }

    public void insertProductDetail(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = modelMapper.map(productDetailDTO, ProductDetail.class);
        productDetailRepository.save(productDetail);
    }

    public void insertProductVariants(ProductVariants productVariants) {
        productVariantsRepository.save(productVariants);
    }

    public List<CateForProdRegisterDTO> getProductCateByParent(int productCateId) {

        List<ProductCate> productCateList = categoryRepository.findAll();
        List<CateForProdRegisterDTO> cateForProdRegisterDTOList = new ArrayList<>();

        for (ProductCate productCate : productCateList) {
            if (productCate.getParent() != null && productCate.getParent().getProductCateId() == productCateId) {

                cateForProdRegisterDTOList.add(CateForProdRegisterDTO.builder()
                        .productCateId(productCate.getProductCateId())
                        .name(productCate.getName())
                        .depth(productCate.getDepth())
                        .build()
                );
            }
        }
        return cateForProdRegisterDTOList;
    }

    public String uploadProdImg(MultipartFile file) {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File fileUploadPath = new File(uploadDir);

        // 파일 업로드 디렉터리가 존재하지 않으면 디렉터리 생성
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdirs();
        }

        if (!file.isEmpty()) {
            String oName = file.getOriginalFilename();
            assert oName != null;
            String ext = oName.substring(oName.lastIndexOf("."));
            String sName = UUID.randomUUID().toString() + ext;

            // 파일 저장
            try {
                file.transferTo(new File(uploadDir + sName));
            } catch (IOException e) {
                log.error(e);
            }

            return sName;
        }

        return null;
    }





}



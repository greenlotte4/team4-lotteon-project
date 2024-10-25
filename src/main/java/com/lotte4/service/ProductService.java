package com.lotte4.service;

import com.lotte4.dto.CateForProdRegisterDTO;
import com.lotte4.dto.ProductDTO;
import com.lotte4.dto.ProductDetailDTO;
import com.lotte4.dto.ProductVariantsDTO;
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
import java.util.*;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductDTO insertProduct(ProductDTO productDTO) {
        Product product = modelMapper.map(productDTO, Product.class);
        Product savedEntity = productRepository.save(product);
        return modelMapper.map(savedEntity, ProductDTO.class);
    }

    public ProductDetailDTO insertProductDetail(ProductDetailDTO productDetailDTO) {
        ProductDetail productDetail = modelMapper.map(productDetailDTO, ProductDetail.class);
        ProductDetail savedEntity = productDetailRepository.save(productDetail);
        return modelMapper.map(savedEntity, ProductDetailDTO.class);
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

    public ProductDTO getProductById(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return modelMapper.map(product, ProductDTO.class);
        }
        return null;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : productList) {
            productDTOList.add(modelMapper.map(product, ProductDTO.class));
        }
        return productDTOList;
    }


    public String uploadProdImg(MultipartFile file) {

        String uploadDir = System.getProperty("user.dir") + "/uploads/product/";
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
                file.transferTo(new File(uploadDir + "prod_img_" + sName));
            } catch (IOException e) {
                log.error(e);
            }

            return sName;
        }

        return null;
    }


    public void makeProductVariantDTOAndInsert(String prodONames, String prodPrices, String prodStocks, String mixedValuesList) {

        // substring을 활용하여 String -> List로 변환
        List<String> names;
        names = List.of(prodONames.substring(1, prodONames.length() - 1).split(","));

        // substring을 활용하여 String -> List로 변환 + int타입으로 변환
        List<String> prices1;
        prices1 = List.of(prodPrices.substring(1, prodPrices.length() - 1).split(","));

        List<String> prices2 = new ArrayList<>(List.of());
        for (String price : prices1) {
            prices2.add(price.substring(1, price.length() - 1));
        }
        List<Integer> prices3 = prices2.stream().map(Integer::parseInt)  // 각 문자열을 Integer로 변환
                .toList();


        // substring을 활용하여 String -> List로 변환 + int타입으로 변환
        List<String> stocks1;
        stocks1 = List.of(prodStocks.substring(1, prodPrices.length() - 1).split(","));

        List<String> stocks2 = new ArrayList<>(List.of());
        for (String stock : stocks1) {
            stocks2.add(stock.substring(1, stock.length() - 1));
        }
        List<Integer> stocks3 = stocks2.stream().map(Integer::parseInt)  // 각 문자열을 Integer로 변환
                .toList();

        log.info("mixedValuesList: " + mixedValuesList);

        // substring을 활용하여 String -> List로 변환

         String[] values = mixedValuesList.substring(1, mixedValuesList.length() - 1).split("\\], \\[");

        // 동일한 인덱스의 값을 동시에 가져오는 반복문
        for (int i = 0; i < names.size(); i++) {
            ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();

            String sku = names.get(i);
            int price = prices3.get(i);
            int stock = stocks3.get(i);

            // 문자열 앞뒤의 대괄호 제거
            values[i] = values[i].replace("[", "").replace("]", "");
            String key = values[i].split(", ")[0].toString();
            String value = values[i].split(", ")[1].toString();

            Map<String, String> options = new HashMap<>();

            options.put(key, value);

            productVariantsDTO.setSku(sku);
            productVariantsDTO.setPrice(price);
            productVariantsDTO.setStock(stock);
            productVariantsDTO.setOptions(options);

            productVariantsRepository.save(modelMapper.map(productVariantsDTO, ProductVariants.class));
        }
    }



}



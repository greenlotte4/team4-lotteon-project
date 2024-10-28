package com.lotte4.service;

import com.lotte4.dto.*;
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

/*
    수정이력

        2024-10-26
        전규찬 - formdata로 받은 String 값들을 replace / split 함수를 통해 가공하여 원하는
                데이터 타입으로 변환하는 static 메서드 생성

        - 2024/10/28 강중원 - 카테고리에 따른 상품 불러오기 기능 추가
*/

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    //
    public static List<String> stringToStringList(String string) {

        List<String> stringList = List.of(string.replace(" ", "").replace("[", "").replace("]", "").replace("\"", "").trim().split(","));

        return stringList;
    }

    public static List<Integer> stringToIntegerList(String string) {

        List<String> stringList = List.of(string.replace(" ", "").replace("[", "").replace("]", "").replace("\"", "").trim().split(","));
        List<Integer> intList = stringList.stream().map(Integer::parseInt).toList();

        return intList;
    }

    public static List<List<String>> stringToListInList(String string) {
        List<String> stringList = List.of(string.replace(" ", "").replace("\"[[", "").replace("]]\"", "").split("],\\["));
        List<List<String>> optionValues = new ArrayList<>();

        for (String s : stringList) {
            List<String> strings = List.of(s.split(","));
            optionValues.add(strings);
        }

        return optionValues;
    }

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

            return "prod_img_" + sName;
        }

        return null;
    }


    public void makeProductVariantDTOAndInsert(String optionNames, String prodONames, String prodPrices, String prodStocks, String mixedValuesList, String productId) {

        // 각 string 들을 알맞은 list 나 map 형식으로 바꿔주기
        List<String> option_names = stringToStringList(optionNames);
        List<String> prod_oNames = stringToStringList(prodONames);
        List<Integer> prod_prices = stringToIntegerList(prodPrices);
        List<Integer> prod_stocks = stringToIntegerList(prodStocks);
        List<List<String>> mixed_values_list = stringToListInList(mixedValuesList);
        int prodId = Integer.parseInt(productId.replace("\"", ""));

        // 리스트 크기 검증
        int size = prod_oNames.size();
        if (prod_prices.size() != size || prod_stocks.size() != size || mixed_values_list.size() != size) {
            throw new IllegalArgumentException("모든 리스트의 크기가 동일해야 합니다.");
        }

        // 동일한 인덱스의 값을 동시에 가져오는 반복문
        for (int i = 0; i < prod_oNames.size(); i++) {

            ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();
            Map<List<String>, List<String>> options = new LinkedHashMap<>();

            String sku = prod_oNames.get(i);
            int price = prod_prices.get(i);
            int stock = prod_stocks.get(i);
            options.put(option_names, mixed_values_list.get(i));

            productVariantsDTO.setSku(sku);
            productVariantsDTO.setPrice(price);
            productVariantsDTO.setStock(stock);
            productVariantsDTO.setOptions(options);
            productVariantsDTO.setProduct(getProductById(prodId));

            productVariantsRepository.save(modelMapper.map(productVariantsDTO, ProductVariants.class));
        }
    }

    public List<ProductDTO> getProductWithCate(int cate){
        List<ProductDTO> productDTOList = new ArrayList<>();
        Optional<ProductCate> productCate = categoryRepository.findById(cate);


        log.info(productDTOList.toString());


        if (productCate.isPresent()) {
            // depth가 1 또는 2인 경우, 하위 카테고리의 제품을 가져옴
            if (productCate.get().getDepth() < 3) {
                for (ProductCate child : productCate.get().getChildren()) {
                    // 현재 하위 카테고리에 속한 제품을 가져옴
                    List<Product> childProducts = productRepository.findByProductCateId(child);
                    for (Product product : childProducts) {
                        productDTOList.add(modelMapper.map(product, ProductDTO.class));
                    }

                    // depth가 1인 경우, 손자 카테고리의 제품을 가져옴
                    if (child.getDepth() < 2) {
                        for (ProductCate grandChild : child.getChildren()) {
                            List<Product> grandChildProducts = productRepository.findByProductCateId(grandChild);
                            for (Product product : grandChildProducts) {
                                productDTOList.add(modelMapper.map(product, ProductDTO.class));
                            }
                        }
                    }
                }
            }

            // 해당 카테고리에 속한 직접적인 제품도 가져옴
            List<Product> products = productRepository.findByProductCateId(productCate.get());
            for (Product product : products) {
                productDTOList.add(modelMapper.map(product, ProductDTO.class));
            }
        }
        log.info(productDTOList.toString());
        return productDTOList;
    }
}



package com.lotte4.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.transaction.annotation.Transactional;
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
        - 2024/10/29 강중원 - 상위 카테고리 리스트로 불러오는 기능 추가
        - 2024/10/31 전규찬 - JSON 문자열을 LinkedHashMap으로 변환하고 productDTO에 set하는 메서드 추가
                           - 파일 수정 발생 시 upload 에서 기존 파일 삭제 + 신규 파일 업로드, db에 파일명 업데이트
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
    private final ObjectMapper objectMapper;

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

    public Product_V_DTO getProductById(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return modelMapper.map(product, Product_V_DTO.class);
        }
        return null;
    }

    public Product_V_DTO getProductById2(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return new Product_V_DTO(product);
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

    public ProductDTO getProductWithoutVariantsById(int productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            return modelMapper.map(product, ProductDTO.class);
        }
        return null;
    }

    public ProductDetailDTO getProductDetailById(int productId) {
        ProductDetail productDetail = productDetailRepository.findByProductId(productId);
        log.info("productDetail : " + productDetail);
        return modelMapper.map(productDetail, ProductDetailDTO.class);
    }

    public String uploadAndDeleteProdImg(MultipartFile file, String prevFileName) {

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

            // 이전 파일명을 입력했을 경우 (null 이 아닌 경우)
            if (prevFileName != null) {
                File file1 = new File(uploadDir + prevFileName);
                try {
                    boolean deleted = file1.delete();
                } catch (Exception e) {
                    log.error(e);
                }
            }

            return "prod_img_" + sName;
        }


        return null;
    }


    @Transactional
    public void makeVariantDTOAndInsert(String optionNames, String prodONames, String prodPrices, String prodStocks, String mixedValuesList, String productId) {

        try {
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
        } catch (Exception e) {
            log.error(e);
        }

    }

    @Transactional
    public void makeVariantDTOAndUpdate(String prodONames, String prodPrices, String prodStocks, String variantsIds, String valuesList, String optionNames, String productId) {

        try {
            // 각 string 들을 알맞은 list 나 map 형식으로 바꿔주기
            List<String> prod_oNames = stringToStringList(prodONames);
            List<Integer> prod_prices = stringToIntegerList(prodPrices);
            List<Integer> prod_stocks = stringToIntegerList(prodStocks);
            List<Integer> variants_ids = stringToIntegerList(variantsIds);
            List<List<String>> values_list = stringToListInList(valuesList);
            List<String> option_names = stringToStringList(optionNames);
            int prodId = Integer.parseInt(productId.replace("\"", ""));

            log.info("values_list = " + values_list);


            // 리스트 크기 검증
            int size = prod_oNames.size();
            if (prod_prices.size() != size || prod_stocks.size() != size || variants_ids.size() != size || values_list.size() != size) {
                throw new IllegalArgumentException("모든 리스트의 크기가 동일해야 합니다.");
            }

            // 동일한 인덱스의 값을 동시에 가져오는 반복문
            for (int i = 0; i < prod_oNames.size(); i++) {

                ProductVariantsDTO productVariantsDTO = new ProductVariantsDTO();
                Map<List<String>, List<String>> options = new LinkedHashMap<>();

                String sku = prod_oNames.get(i);
                int price = prod_prices.get(i);
                int stock = prod_stocks.get(i);
                int variantId = variants_ids.get(i);
                options.put(option_names, values_list.get(i));

                productVariantsDTO.setSku(sku);
                productVariantsDTO.setPrice(price);
                productVariantsDTO.setStock(stock);
                productVariantsDTO.setVariant_id(variantId);
                productVariantsDTO.setOptions(options);
                productVariantsDTO.setProduct(getProductById(prodId));

                log.info("productVariantsDTO : " + productVariantsDTO);

                productVariantsRepository.save(modelMapper.map(productVariantsDTO, ProductVariants.class));
            }
        } catch (Exception e) {
            log.error(e);
        }
    }


    public String deleteById(int productId) {
        productRepository.deleteById(productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return "failure";
        }
        return "success";
    }

    public List<ProductCateDTO> getProductCates(int cate) {
        List<ProductCateDTO> productCateDTOList = new ArrayList<>();
        Optional<ProductCate> productCateOpt = categoryRepository.findById(cate);
        if (productCateOpt.isPresent()) {
            ProductCate productCate = productCateOpt.get();
            productCateDTOList.add(modelMapper.map(productCate, ProductCateDTO.class));
            while (productCate.getParent() != null) {
                productCate = productCate.getParent();
                productCateDTOList.add(modelMapper.map(productCate, ProductCateDTO.class));
            }
        }
        Collections.reverse(productCateDTOList);
        return productCateDTOList;
    }

    public List<ProductDTO> getProductWithCate(int cate) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        Optional<ProductCate> productCate = categoryRepository.findById(cate);


        log.info(productDTOList.toString());


        if (productCate.isPresent()) {
            // depth가 1 또는 2인 경우, 하위 카테고리의 제품을 가져옴
            if (productCate.get().getChildren() != null) {
                for (ProductCate child : productCate.get().getChildren()) {
                    // 현재 하위 카테고리에 속한 제품을 가져옴
                    List<Product> childProducts = productRepository.findByProductCateId(child);
                    for (Product product : childProducts) {
                        productDTOList.add(modelMapper.map(product, ProductDTO.class));
                    }

                    // depth가 1인 경우, 손자 카테고리의 제품을 가져옴
                    if (child.getChildren() != null) {
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

    public ProductDTO JsonToMapAndSetProductDTO(String optionsJson, ProductDTO productDTO) {

        // 'options' JSON 문자열을 LinkedHashMap<String, List<String>>으로 변환
        LinkedHashMap<String, List<String>> optionsMap = null;
        try {
            optionsMap = objectMapper.readValue(optionsJson,
                    new TypeReference<LinkedHashMap<String, List<String>>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        productDTO.setOptions(optionsMap);
        return productDTO;
    }

    public ProductDTO updateProdImg(MultipartFile img1, MultipartFile img2, MultipartFile img3, MultipartFile detail, ProductDTO productDTO, Product_V_DTO product_V_DTO) {

        if (img1 != null) {
            String prevImg = productDTO.getImg1();
            String newImg1 = uploadAndDeleteProdImg(img1, prevImg);
            productDTO.setImg1(newImg1);
        } else {
            productDTO.setImg1(product_V_DTO.getImg1());
        }
        if (img2 != null) {
            String prevImg = productDTO.getImg2();
            String newImg2 = uploadAndDeleteProdImg(img2, prevImg);
            productDTO.setImg2(newImg2);
        } else {
            productDTO.setImg2(product_V_DTO.getImg2());
        }
        if (img3 != null) {
            String prevImg = productDTO.getImg3();
            String newImg3 = uploadAndDeleteProdImg(img3, prevImg);
            productDTO.setImg3(newImg3);
        } else {
            productDTO.setImg3(product_V_DTO.getImg3());
        }
        if (detail != null) {
            String prevImg = productDTO.getDetail();
            String newDetail = uploadAndDeleteProdImg(detail, prevImg);
            productDTO.setDetail(newDetail);
        } else {
            productDTO.setDetail(product_V_DTO.getDetail());
        }
        return productDTO;
    }

}



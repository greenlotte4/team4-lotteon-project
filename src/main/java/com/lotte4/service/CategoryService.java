package com.lotte4.service;

import com.lotte4.dto.CartDTO;
import com.lotte4.dto.ProductCateChildDTO;
import com.lotte4.dto.ProductCateDTO;
import com.lotte4.entity.ProductCate;
import com.lotte4.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
    2024/10/30 캐싱 어노테이션 추가
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductCateDTO getProductCate(int productCateId){

        ProductCate productCate = categoryRepository.findById(productCateId).orElseThrow(NullPointerException::new);

        return modelMapper.map(productCate, ProductCateDTO.class);
    }

    public int getProductCateIdByName(String name){
        ProductCate productCate = categoryRepository.findByName(name);
        if (productCate == null) {
            return 0;
        }
        return productCate.getProductCateId();
    }


    public List<ProductCateDTO> getALLProductCate(){

        List<ProductCate> productCateList = categoryRepository.findAll();
        List<ProductCateDTO> productCateDTOList = new ArrayList<>();

        for(ProductCate productCate : productCateList){
            productCateDTOList.add(new ProductCateDTO(productCate));
        }
        return productCateDTOList;
    }

    public void insertProductCate(ProductCateDTO productCateDTO, String parent, int depth){

        ProductCate productCate = modelMapper.map(productCateDTO, ProductCate.class);

        //depth가 1보다 큰경우
        //--> 제일 상위 카테고리가 아닌경우
        if(depth > 1){
            ProductCate parentCate = categoryRepository.findByName(parent);
            productCate.setParent(parentCate);
        }

        productCate.setDepth(depth);

        categoryRepository.save(productCate);
    }
    @Cacheable(key = "'getProductCateListWithDepth'", value = "categories", cacheManager = "redisCacheManager")
    public List<ProductCateChildDTO> getProductCateListWithDepth(int depth){
        List<ProductCate> productCateList = categoryRepository.findByDepth(depth);

        List<ProductCateChildDTO> childDTOS = productCateList.stream().map(ProductCateChildDTO::new).collect(Collectors.toList());

        return childDTOS;
    }

    public boolean deleteProductCate(String name){

        ProductCate productCate = categoryRepository.findByName(name);
        if(productCate != null){
            categoryRepository.delete(productCate);
            return true;
        }
        else {
            return false;
        }

    }
}

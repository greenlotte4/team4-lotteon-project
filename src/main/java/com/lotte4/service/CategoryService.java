package com.lotte4.service;

import com.lotte4.dto.ProductCateDTO;
import com.lotte4.dto.ProductRegisterCateDTO;
import com.lotte4.entity.ProductCate;
import com.lotte4.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public void insertProductCate(ProductRegisterCateDTO productRegisterCateDTO, String parent){
        log.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        ProductCate productCate = modelMapper.map(productRegisterCateDTO, ProductCate.class);

        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        //depth가 1보다 큰경우
        //--> 제일 상위 카테고리가 아닌경우
        if(productCate.getDepth() > 1){
            ProductCate parentCate = categoryRepository.findByName(parent);
            productCate.setParent(parentCate);
        }

        categoryRepository.save(productCate);
    }

    public List<ProductCateDTO> getProductCateListWithDepth(int depth){

        List<ProductCateDTO> productCateDTOList = new ArrayList<>();
        List<ProductCate> productCateList = categoryRepository.findByDepth(depth);

        for(ProductCate productCate : productCateList){
            productCateDTOList.add(new ProductCateDTO(productCate));
        }
        return productCateDTOList;

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

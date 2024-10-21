package com.lotte4.service;

import com.lotte4.dto.ProductCateDTO;
import com.lotte4.entity.ProductCate;
import com.lotte4.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public ProductCateDTO getProductCate(int productCateId){

        ProductCate productCate = categoryRepository.findById(productCateId).orElseThrow(NullPointerException::new);

        return new ProductCateDTO(productCate);
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

    public List<ProductCateDTO> getProductCateListWithDepth(int depth){
        List<ProductCateDTO> productCateDTOList = new ArrayList<>();
        List<ProductCate> productCateList = categoryRepository.findByDepth(depth);

        for(ProductCate productCate : productCateList){
            productCateDTOList.add(new ProductCateDTO(productCate));
        }
        return productCateDTOList;
    }
}

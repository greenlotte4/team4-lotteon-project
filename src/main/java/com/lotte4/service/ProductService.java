package com.lotte4.service;

import com.lotte4.dto.CateForProdRegisterDTO;
import com.lotte4.dto.ProductCateDTO;
import com.lotte4.entity.Product;
import com.lotte4.entity.ProductCate;
import com.lotte4.entity.ProductDetail;
import com.lotte4.entity.ProductVariants;
import com.lotte4.repository.CategoryRepository;
import com.lotte4.repository.ProductDetailRepository;
import com.lotte4.repository.ProductRepository;
import com.lotte4.repository.ProductVariantsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductVariantsRepository productVariantsRepository;
    private final ProductDetailRepository productDetailRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public void insertProduct(Product product) {
        productRepository.save(product);
    }

    public void insertProductDetail(ProductDetail productDetail) {
        productDetailRepository.save(productDetail);
    }

    public void insertProductVariants(ProductVariants productVariants) {
        productVariantsRepository.save(productVariants);
    }

    public List<CateForProdRegisterDTO> getProductCateByParent(int productCateId){

        List<ProductCate> productCateList = categoryRepository.findAll();
        List<CateForProdRegisterDTO> cateForProdRegisterDTOList = new ArrayList<>();

        for (ProductCate productCate : productCateList) {
            if(productCate.getParent() !=null && productCate.getParent().getProductCateId() == productCateId){

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

}

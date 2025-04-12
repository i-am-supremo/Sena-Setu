package com.defense.inventory.service;

import com.defense.inventory.dto.ProductRequestDto;
import com.defense.inventory.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto product, Long companyId);

    ProductResponseDto getProductById(Long productId);

    List<ProductResponseDto> getAllProducts();

    List<ProductResponseDto> searchProductByName(String productName);

    ProductResponseDto updateProduct(Long productId, ProductRequestDto updatedProduct, Long companyId);

    String deleteProduct(Long productId);

    List<ProductResponseDto> getProductsByCompanyId(Long companyId);
}

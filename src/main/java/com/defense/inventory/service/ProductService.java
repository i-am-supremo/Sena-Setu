package com.defense.inventory.service;

import com.defense.inventory.dto.ProductRequestDto;
import com.defense.inventory.dto.ProductResponseDto;
import com.defense.inventory.entity.Product;

import java.util.List;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto product, Long companyId);

    ProductResponseDto getProductById(Long productId);

    List<ProductResponseDto> getAllProducts();

    List<ProductResponseDto> searchProductByName(String productName);

    ProductResponseDto updateProduct(Long productId, ProductRequestDto updatedProduct, Long companyId);

    String deleteProduct(Long productId);
}

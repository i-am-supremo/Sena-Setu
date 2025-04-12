package com.defense.inventory.service;

import com.defense.inventory.dto.SubProductRequestDto;
import com.defense.inventory.dto.SubProductResponseDto;

import java.util.List;

public interface SubProductService {

    SubProductResponseDto createSubProduct(SubProductRequestDto subProduct, Long productId);

    SubProductResponseDto getSubProductById(Long subProductId);

    List<SubProductResponseDto> getAllSubProducts();

    List<SubProductResponseDto> getSubProductsByName(String subProductName);

    SubProductResponseDto updateSubProduct(Long subProductId, SubProductRequestDto updatedSubProduct, Long productId);

    String deleteSubProduct(Long subProductId);

    List<SubProductResponseDto> getSubProductsByProductId(Long productId);
}

package com.defense.inventory.service;

import com.defense.inventory.dto.SubProductRequestDto;
import com.defense.inventory.dto.SubProductResponseDto;
import com.defense.inventory.entity.SubProduct;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.List;

public interface SubProductService {

    SubProductResponseDto createSubProduct(SubProductRequestDto subProduct, Long productId);

    SubProductResponseDto getSubProductById(Long subProductId);

    List<SubProductResponseDto> getAllSubProducts();

    List<SubProductResponseDto> getSubProductsByName(String subProductName);

    SubProductResponseDto updateSubProduct(Long subProductId, SubProductRequestDto updatedSubProduct, Long productId);

    String deleteSubProduct(Long subProductId);
}

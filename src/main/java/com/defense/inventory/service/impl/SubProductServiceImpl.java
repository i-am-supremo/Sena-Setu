package com.defense.inventory.service.impl;

import com.defense.inventory.dto.SubProductRequestDto;
import com.defense.inventory.dto.SubProductResponseDto;
import com.defense.inventory.entity.Product;
import com.defense.inventory.entity.SubProduct;
import com.defense.inventory.exception.ResourceAlreadyExistException;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.ProductRepository;
import com.defense.inventory.repository.SubProductRepository;
import com.defense.inventory.service.SubProductService;
import com.defense.inventory.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubProductServiceImpl implements SubProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final SubProductRepository subProductRepository;
    private final LoggerServiceImpl loggerService;

    @Override
    public SubProductResponseDto createSubProduct(SubProductRequestDto subProductRequestDto, Long productId) {
        log.info("Fetching Product From the repo");
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product ", "id ", productId));
        SubProduct subProduct = modelMapper.map(subProductRequestDto, SubProduct.class);
        subProduct.setProduct(product);
        String barcode;
        do {
            barcode = this.generateRandomBarcode();
        } while (subProductRepository.existsByBarcode(barcode));
        subProduct.setBarcode(barcode);
        if (subProductRepository.existsByNameAndProductId(subProductRequestDto.getName(), productId)) {
            throw new ResourceAlreadyExistException(subProductRequestDto.getName()+" Already ","This Product ",product.getName());
        }
        log.info("Saving subProduct ");
        loggerService.saveLoggingDetails(AppConstants.CREATED_SUB_PRODUCT, subProduct.getName());
        return modelMapper.map(subProductRepository.save(subProduct), SubProductResponseDto.class);
    }

    @Override
    public SubProductResponseDto getSubProductById(Long subProductId) {
        log.info("Getting subProduct details by Id ");
        return modelMapper.map(subProductRepository.findById(subProductId).orElseThrow(() -> new ResourceNotFoundException("No Sub Product ", "id ", subProductId)), SubProductResponseDto.class);
    }

    @Override
    public List<SubProductResponseDto> getAllSubProducts() {
        log.info("Getting all products details");
        return subProductRepository.findAll()
                .stream()
                .map(subProduct -> modelMapper.map(subProduct, SubProductResponseDto.class))
                .toList();
    }

    @Override
    public List<SubProductResponseDto> getSubProductsByName(String subProduct) {
        return subProductRepository.findByNameContainingIgnoreCase(subProduct)
                .stream().map(product -> modelMapper.map(product, SubProductResponseDto.class)).toList();
    }

    @Override
    public SubProductResponseDto updateSubProduct(Long subProductId, SubProductRequestDto updatedSubProduct, Long productId) {
        SubProduct subProduct = subProductRepository.findById(subProductId).orElseThrow(() -> new ResourceNotFoundException("No Sub Product ", "id ", subProductId));
        subProduct.setName(updatedSubProduct.getName());
        subProduct.setQuantity(updatedSubProduct.getQuantity());
        if (subProduct.getProduct().getId() != productId) {
            Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product ", "id ", productId));
            subProduct.setProduct(product);
        }
        loggerService.saveLoggingDetails(AppConstants.UPDATED_SUB_PRODUCT, subProduct.getName());
        return modelMapper.map(subProductRepository.save(subProduct), SubProductResponseDto.class);
    }

    @Override
    public String deleteSubProduct(Long subProductId) {
        SubProduct product = subProductRepository.findById(subProductId).orElseThrow(() -> new ResourceNotFoundException("No Sub Product ", "id ", subProductId));
        log.info("Deleting Product {}", product.getName());
        loggerService.saveLoggingDetails(AppConstants.DELETED_SUB_PRODUCT, product.getName());
        subProductRepository.delete(product);
        return "Product Deleted Successfully";

    }

    @Override
    public List<SubProductResponseDto> getSubProductsByProductId(Long productId) {
        List<SubProduct> subProducts = subProductRepository.findByProductId(productId);
        return subProducts.stream()
                .map(subProduct -> modelMapper.map(subProduct, SubProductResponseDto.class))
                .collect(Collectors.toList());
    }

    private String generateRandomBarcode() {
        Random random = new Random();
        StringBuilder barcode = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            barcode.append(random.nextInt(10));
        }
        return barcode.toString();
    }
}

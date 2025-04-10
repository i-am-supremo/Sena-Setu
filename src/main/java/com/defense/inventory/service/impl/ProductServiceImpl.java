package com.defense.inventory.service.impl;

import com.defense.inventory.dto.ProductRequestDto;
import com.defense.inventory.dto.ProductResponseDto;
import com.defense.inventory.entity.Company;
import com.defense.inventory.entity.Product;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.CompanyRepository;
import com.defense.inventory.repository.ProductRepository;
import com.defense.inventory.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto, Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", companyId));
        Product product = modelMapper.map(productRequestDto, Product.class);
        product.setCompany(company);
        return modelMapper.map(productRepository.save(product), ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getProductById(Long productId) {
        log.info("Getting product details by Id");
        return modelMapper.map(productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product ", "id ", productId)), ProductResponseDto.class);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        log.info("Getting all products details");
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .toList();
    }

    @Override
    public List<ProductResponseDto> searchProductByName(String productName) {
        return productRepository.findByNameContainingIgnoreCase(productName)
                .stream().map(product -> modelMapper.map(product, ProductResponseDto.class)).toList();
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto updatedProduct, Long companyId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product ", "id ", productId));
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        if (product.getCompany().getId() != companyId) {
            Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", companyId));
            product.setCompany(company);
        }
        return modelMapper.map(productRepository.save(product), ProductResponseDto.class);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product ", "id ", productId));
        log.info("Deleting Product {}", product.getName());
        productRepository.delete(product);
        return "Product Deleted Successfully";
    }
}

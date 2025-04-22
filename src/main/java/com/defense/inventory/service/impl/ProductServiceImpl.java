package com.defense.inventory.service.impl;

import com.defense.inventory.dto.ProductRequestDto;
import com.defense.inventory.dto.ProductResponseDto;
import com.defense.inventory.entity.Company;
import com.defense.inventory.entity.Product;
import com.defense.inventory.exception.ResourceAlreadyExistException;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.CompanyRepository;
import com.defense.inventory.repository.ProductRepository;
import com.defense.inventory.service.ProductService;
import com.defense.inventory.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final LoggerServiceImpl loggerService;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto, Long companyId) {
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", companyId));
        Product product = modelMapper.map(productRequestDto, Product.class);
        product.setName(productRequestDto.getName().trim());
        product.setCompany(company);
        if (productRepository.findByNameIgnoreCaseAndCompanyId(product.getName(), companyId)!=null) {
            throw new ResourceAlreadyExistException(product.getName() + " Already ", "This Company ", company.getName());
        }
        loggerService.saveLoggingDetails(AppConstants.CREATED_PRODUCT, product.getName());
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
        product.setName(updatedProduct.getName().trim());
        product.setDescription(updatedProduct.getDescription());
        if (product.getCompany().getId() != companyId) {
            Company company = companyRepository.findById(companyId).orElseThrow(() -> new ResourceNotFoundException("No Company ", "id ", companyId));
            product.setCompany(company);
        }
        Product alreadyExist = productRepository.findByNameIgnoreCaseAndCompanyId(updatedProduct.getName().trim(), companyId);
        if (alreadyExist != null && alreadyExist.getId() != productId) {
            throw new ResourceAlreadyExistException(updatedProduct.getName() + " Already ", "This Company ", product.getCompany().getName());
        }
        loggerService.saveLoggingDetails(AppConstants.UPDATED_PRODUCT, product.getName());
        return modelMapper.map(productRepository.save(product), ProductResponseDto.class);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("No Product ", "id ", productId));
        log.info("Deleting Product {}", product.getName());
        loggerService.saveLoggingDetails(AppConstants.DELETED_PRODUCT, product.getName());
        productRepository.delete(product);
        return "Product Deleted Successfully";
    }

    @Override
    public List<ProductResponseDto> getProductsByCompanyId(Long companyId) {
        List<Product> products = productRepository.findByCompanyId(companyId);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }
}

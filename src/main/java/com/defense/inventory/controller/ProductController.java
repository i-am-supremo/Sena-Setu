package com.defense.inventory.controller;

import com.defense.inventory.dto.ProductRequestDto;
import com.defense.inventory.dto.ProductResponseDto;
import com.defense.inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Slf4j
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/company/{companyId}")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto,
                                                            @PathVariable("companyId") Long companyId) {
        log.info("Creating product for company ID: {}", companyId);
        return ResponseEntity.ok(productService.createProduct(productRequestDto, companyId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long productId) {
        log.info("Fetching product with ID: {}", productId);
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        log.info("Fetching all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProductByName(@RequestParam("name") String productName) {
        log.info("Searching products with name containing: {}", productName);
        return ResponseEntity.ok(productService.searchProductByName(productName));
    }

    @PutMapping("/{id}/company/{companyId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long productId,
                                                            @Valid @RequestBody ProductRequestDto updatedProduct,
                                                            @PathVariable("companyId") Long companyId) {
        log.info("Updating product with ID: {}", productId);
        return ResponseEntity.ok(productService.updateProduct(productId, updatedProduct, companyId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        log.info("Deleting product with ID: {}", productId);
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
}

package com.defense.inventory.controller;

import com.defense.inventory.dto.ProductRequestDto;
import com.defense.inventory.dto.ProductResponseDto;
import com.defense.inventory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Creates a new Product in the DB",
            description = "Adds a new product in the DB"
    )
    @PostMapping("/company/{companyId}")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto,
                                                            @PathVariable("companyId") Long companyId) {
        log.info("Creating product for company ID: {}", companyId);
        return ResponseEntity.ok(productService.createProduct(productRequestDto, companyId));
    }

    @Operation(
            summary = "Return a Product by Id",
            description = "Return the Product Detail by Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable("id") Long productId) {
        log.info("Fetching product with ID: {}", productId);
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @Operation(
            summary = "Return all Products available",
            description = "Return all the product available"
    )
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        log.info("Fetching all products");
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @Operation(
            summary = "Return List of Product Searched by a keyword",
            description = "Return List of Product Searched by a keyword"
    )
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProductByName(@RequestParam("name") String productName) {
        log.info("Searching products with name containing: {}", productName);
        return ResponseEntity.ok(productService.searchProductByName(productName));
    }

    @Operation(
            summary = "Update product details by Id",
            description = "Update product by Id"
    )
    @PutMapping("/{id}/company/{companyId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable("id") Long productId,
                                                            @Valid @RequestBody ProductRequestDto updatedProduct,
                                                            @PathVariable("companyId") Long companyId) {
        log.info("Updating product with ID: {}", productId);
        return ResponseEntity.ok(productService.updateProduct(productId, updatedProduct, companyId));
    }

    @Operation(
            summary = "Delete product by Id",
            description = "Delete Product by Id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long productId) {
        log.info("Deleting product with ID: {}", productId);
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }
}

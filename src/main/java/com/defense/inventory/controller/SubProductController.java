package com.defense.inventory.controller;

import com.defense.inventory.dto.SubProductRequestDto;
import com.defense.inventory.dto.SubProductResponseDto;
import com.defense.inventory.service.SubProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-products")
@Slf4j
@RequiredArgsConstructor
public class SubProductController {

    private final SubProductService subProductService;

    @Operation(
            summary = "Creates a new Sub-Product in the DB",
            description = "Adds a new sub-product in the DB"
    )
    @PostMapping("/product/{productId}")
    public ResponseEntity<SubProductResponseDto> createSubProduct(@Valid @RequestBody SubProductRequestDto subProductRequestDto,
                                                                  @PathVariable("productId") Long productId) {
        log.info("Creating sub-product for product ID: {}", productId);
        return ResponseEntity.ok(subProductService.createSubProduct(subProductRequestDto, productId));
    }

    @Operation(
            summary = "Return a Sub-Product by Id",
            description = "Return the Sub-Product Detail by Id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<SubProductResponseDto> getSubProductById(@PathVariable("id") Long subProductId) {
        log.info("Fetching sub-product with ID: {}", subProductId);
        return ResponseEntity.ok(subProductService.getSubProductById(subProductId));
    }

    @Operation(
            summary = "Return all Sub-Products available",
            description = "Return all the sub-product available"
    )
    @GetMapping
    public ResponseEntity<List<SubProductResponseDto>> getAllSubProducts() {
        log.info("Fetching all sub-products");
        return ResponseEntity.ok(subProductService.getAllSubProducts());
    }

    @Operation(
            summary = "Return List of Sub-Product Searched by a keyword",
            description = "Return List of Sub-Product Searched by a keyword"
    )
    @GetMapping("/search")
    public ResponseEntity<List<SubProductResponseDto>> getSubProductsByName(@RequestParam("name") String subProductName) {
        log.info("Searching sub-products with name containing: {}", subProductName);
        return ResponseEntity.ok(subProductService.getSubProductsByName(subProductName));
    }

    @Operation(
            summary = "Update sub-product details by Id",
            description = "Update sub-product by Id"
    )
    @PutMapping("/{id}/product/{productId}")
    public ResponseEntity<SubProductResponseDto> updateSubProduct(@PathVariable("id") Long subProductId,
                                                                  @Valid @RequestBody SubProductRequestDto updatedSubProduct,
                                                                  @PathVariable("productId") Long productId) {
        log.info("Updating sub-product with ID: {}", subProductId);
        return ResponseEntity.ok(subProductService.updateSubProduct(subProductId, updatedSubProduct, productId));
    }

    @Operation(
            summary = "Delete sub-product by Id",
            description = "Delete sub-Product by Id"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubProduct(@PathVariable("id") Long subProductId) {
        log.info("Deleting sub-product with ID: {}", subProductId);
        return ResponseEntity.ok(subProductService.deleteSubProduct(subProductId));
    }
}

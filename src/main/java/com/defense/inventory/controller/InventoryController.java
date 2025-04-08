package com.defense.inventory.controller;

import com.defense.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@Slf4j
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(
            summary = "Increase the inventory of the created sub-product",
            description = "This will increase the inventory of already created sub-product"
    )
    @PostMapping("/increase/{subProductId}")
    public ResponseEntity<String> increaseInventory(@PathVariable Long subProductId, @RequestParam int quantity) {
        log.info("Increasing inventory for SubProduct ID: {} by {}", subProductId, quantity);
        inventoryService.increaseInventory(subProductId, quantity);
        return ResponseEntity.ok("Inventory increased successfully");
    }

    @Operation(
            summary = "Decrease the inventory of the created sub-product",
            description = "This will decrease the inventory of already created sub-product"
    )
    @PostMapping("/decrease/{subProductId}")
    public ResponseEntity<String> decreaseInventory(@PathVariable Long subProductId, @RequestParam int quantity) {
        log.info("Decreasing inventory for SubProduct ID: {} by {}", subProductId, quantity);
        inventoryService.decreaseInventory(subProductId, quantity);
        return ResponseEntity.ok("Inventory decreased successfully");
    }

    @Operation(
            summary = "Returns the count of inventory of the sub-product",
            description = "This will return the count of the inventory of already created sub-product"
    )
    @GetMapping("/count/{subProductId}")
    public ResponseEntity<Integer> getInventoryCount(@PathVariable Long subProductId) {
        log.info("Fetching inventory count for SubProduct ID: {}", subProductId);
        return ResponseEntity.ok(inventoryService.getInventoryCount(subProductId));
    }
}

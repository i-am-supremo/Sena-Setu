package com.defense.inventory.controller;

import com.defense.inventory.service.InventoryService;
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

    @PostMapping("/increase/{subProductId}")
    public ResponseEntity<String> increaseInventory(@PathVariable Long subProductId, @RequestParam int quantity) {
        log.info("Increasing inventory for SubProduct ID: {} by {}", subProductId, quantity);
        inventoryService.increaseInventory(subProductId, quantity);
        return ResponseEntity.ok("Inventory increased successfully");
    }

    @PostMapping("/decrease/{subProductId}")
    public ResponseEntity<String> decreaseInventory(@PathVariable Long subProductId, @RequestParam int quantity) {
        log.info("Decreasing inventory for SubProduct ID: {} by {}", subProductId, quantity);
        inventoryService.decreaseInventory(subProductId, quantity);
        return ResponseEntity.ok("Inventory decreased successfully");
    }

    @GetMapping("/count/{subProductId}")
    public ResponseEntity<Integer> getInventoryCount(@PathVariable Long subProductId) {
        log.info("Fetching inventory count for SubProduct ID: {}", subProductId);
        return ResponseEntity.ok(inventoryService.getInventoryCount(subProductId));
    }
}

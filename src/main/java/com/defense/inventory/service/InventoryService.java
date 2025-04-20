package com.defense.inventory.service;

public interface InventoryService {
    void updateInventory(Long subProductId, int quantity);

    int getInventoryCount(Long subProductId);
}

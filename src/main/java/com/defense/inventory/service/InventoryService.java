package com.defense.inventory.service;

public interface InventoryService {
    void increaseInventory(Long subProductId, int quantity);

    int getInventoryCount(Long subProductId);
}

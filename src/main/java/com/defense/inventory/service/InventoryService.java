package com.defense.inventory.service;

import com.defense.inventory.dto.SubProductResponseDto;

public interface InventoryService {
    SubProductResponseDto updateInventory(Long subProductId, int quantity);

    int getInventoryCount(Long subProductId);
}

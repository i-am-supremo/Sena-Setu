package com.defense.inventory.service.impl;

import com.defense.inventory.entity.SubProduct;
import com.defense.inventory.exception.ResourceNotFoundException;
import com.defense.inventory.repository.SubProductRepository;
import com.defense.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final SubProductRepository subProductRepository;

    @Override
    public void increaseInventory(Long subProductId, int quantity) {
        SubProduct subProduct = subProductRepository.findById(subProductId).orElseThrow(() -> new ResourceNotFoundException("No Sub Product ", "id ", subProductId));
        int updatedQuantity = subProduct.getQuantity() + quantity;
        subProduct.setQuantity(updatedQuantity);
        subProductRepository.save(subProduct);

    }
    
    @Override
    public int getInventoryCount(Long subProductId) {
        SubProduct subProduct = subProductRepository.findById(subProductId).orElseThrow(() -> new ResourceNotFoundException("No Sub Product ", "id ", subProductId));
        return subProduct.getQuantity();
    }
}

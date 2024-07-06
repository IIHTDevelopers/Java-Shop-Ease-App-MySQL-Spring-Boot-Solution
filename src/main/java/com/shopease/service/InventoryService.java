package com.shopease.service;

import java.util.List;

import com.shopease.dto.InventoryDTO;
import com.shopease.dto.ProductDTO;

public interface InventoryService {
	InventoryDTO addInventoryItem(InventoryDTO inventoryDTO);

	List<InventoryDTO> getAllInventoryItems();

	InventoryDTO getInventoryItemById(Long id);

	InventoryDTO updateInventoryItem(Long id, InventoryDTO inventoryDTO);

	Boolean deleteInventoryItem(Long id);

	List<ProductDTO> getInventoryStatusOfProducts();

	InventoryDTO updateProductInventory(Long productId, InventoryDTO inventoryDTO);
}

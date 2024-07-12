package com.shopease.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopease.dto.InventoryDTO;
import com.shopease.dto.ProductDTO;
import com.shopease.entity.Inventory;
import com.shopease.entity.Product;
import com.shopease.exception.NotFoundException;
import com.shopease.repo.InventoryRepository;
import com.shopease.repo.ProductRepository;
import com.shopease.service.InventoryService;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	private InventoryRepository inventoryRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public InventoryDTO addInventoryItem(InventoryDTO inventoryDTO) {
		Inventory inventory = new Inventory();
		inventory.setLocation(inventoryDTO.getLocation());
		inventory.setStockQuantity(inventoryDTO.getStockQuantity());

		Inventory savedInventory = inventoryRepository.save(inventory);
		inventoryDTO.setId(savedInventory.getId());

		return inventoryDTO;
	}

	@Override
	public List<InventoryDTO> getAllInventoryItems() {
		return inventoryRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public InventoryDTO getInventoryItemById(Long id) {
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Inventory item not found for this id : " + id));
		return convertToDTO(inventory);
	}

	@Override
	public InventoryDTO updateInventoryItem(Long id, InventoryDTO inventoryDTO) {
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Inventory item not found for this id : " + id));

		inventory.setLocation(inventoryDTO.getLocation());
		inventory.setStockQuantity(inventoryDTO.getStockQuantity());

		Inventory updatedInventory = inventoryRepository.save(inventory);
		return convertToDTO(updatedInventory);
	}

	@Override
	public Boolean deleteInventoryItem(Long id) {
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Inventory item not found for this id : " + id));

		inventoryRepository.delete(inventory);
		return true;
	}

	@Override
	public List<ProductDTO> getInventoryStatusOfProducts() {
		return productRepository.findAll().stream().map(this::convertProductToDTO).collect(Collectors.toList());
	}

	@Override
	public InventoryDTO updateProductInventory(Long productId, InventoryDTO inventoryDTO) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found for this id : " + productId));

		Inventory inventory = product.getInventory();
		inventory.setStockQuantity(inventoryDTO.getStockQuantity());
		inventoryRepository.save(inventory);

		return convertToDTO(inventory);
	}

	private InventoryDTO convertToDTO(Inventory inventory) {
		InventoryDTO inventoryDTO = new InventoryDTO();
		inventoryDTO.setId(inventory.getId());
		inventoryDTO.setLocation(inventory.getLocation());
		inventoryDTO.setStockQuantity(inventory.getStockQuantity());
		return inventoryDTO;
	}

	private ProductDTO convertProductToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());
		// Include other fields as necessary
		return productDTO;
	}
}

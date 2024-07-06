package com.shopease.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.dto.InventoryDTO;
import com.shopease.dto.ProductDTO;
import com.shopease.service.InventoryService;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;

	@PostMapping
	public ResponseEntity<InventoryDTO> addInventoryItem(@RequestBody InventoryDTO inventoryDTO) {
		InventoryDTO createdInventory = inventoryService.addInventoryItem(inventoryDTO);
		return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<InventoryDTO>> getAllInventoryItems() {
		List<InventoryDTO> inventoryItems = inventoryService.getAllInventoryItems();
		return new ResponseEntity<>(inventoryItems, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<InventoryDTO> getInventoryItemById(@PathVariable Long id) {
		InventoryDTO inventoryDTO = inventoryService.getInventoryItemById(id);
		return new ResponseEntity<>(inventoryDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<InventoryDTO> updateInventoryItem(@PathVariable Long id,
			@RequestBody InventoryDTO inventoryDTO) {
		InventoryDTO updatedInventory = inventoryService.updateInventoryItem(id, inventoryDTO);
		return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteInventoryItem(@PathVariable Long id) {
		inventoryService.deleteInventoryItem(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> getInventoryStatusOfProducts() {
		List<ProductDTO> productStatus = inventoryService.getInventoryStatusOfProducts();
		return new ResponseEntity<>(productStatus, HttpStatus.OK);
	}

	@PutMapping("/products/{productId}")
	public ResponseEntity<InventoryDTO> updateProductInventory(@PathVariable Long productId,
			@RequestBody InventoryDTO inventoryDTO) {
		InventoryDTO updatedInventory = inventoryService.updateProductInventory(productId, inventoryDTO);
		return new ResponseEntity<>(updatedInventory, HttpStatus.OK);
	}
}

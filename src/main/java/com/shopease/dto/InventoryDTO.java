package com.shopease.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InventoryDTO {

	private Long id;

	@NotBlank
	private String location;

	@NotNull
	private Integer stockQuantity;

	private List<ProductDTO> products;

	public InventoryDTO() {
		super();
	}

	public InventoryDTO(Long id, @NotBlank String location, @NotNull Integer stockQuantity, List<ProductDTO> products) {
		super();
		this.id = id;
		this.location = location;
		this.stockQuantity = stockQuantity;
		this.products = products;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
}

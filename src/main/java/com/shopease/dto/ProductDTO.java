package com.shopease.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

	private Long id;

	@NotBlank
	@Size(max = 100)
	private String name;

	@Size(max = 2000)
	private String description;

	@NotNull
	private Double price;

	private List<ReviewDTO> reviews;

	private InventoryDTO inventory;

	public ProductDTO() {
		super();
	}

	public ProductDTO(Long id, @NotBlank @Size(max = 100) String name, @Size(max = 2000) String description,
			@NotNull Double price, List<ReviewDTO> reviews, InventoryDTO inventory) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.reviews = reviews;
		this.inventory = inventory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<ReviewDTO> getReviews() {
		return reviews;
	}

	public void setReviews(List<ReviewDTO> reviews) {
		this.reviews = reviews;
	}

	public InventoryDTO getInventory() {
		return inventory;
	}

	public void setInventory(InventoryDTO inventory) {
		this.inventory = inventory;
	}
}

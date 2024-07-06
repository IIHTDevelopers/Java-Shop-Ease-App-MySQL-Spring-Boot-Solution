package com.shopease.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class OrderDTO {

	private Long id;

	@NotNull
	private UserDTO user;

	@NotNull
	private ProductDTO product;

	@NotNull
	private Date orderDate;

	@NotBlank
	private String status;

	public OrderDTO() {
		super();
	}

	public OrderDTO(Long id, @NotNull UserDTO user, @NotNull ProductDTO product, @NotNull Date orderDate,
			@NotBlank String status) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
		this.orderDate = orderDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

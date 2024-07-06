package com.shopease.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ReviewDTO {

	private Long id;

	@NotBlank
	private String comment;

	@NotNull
	@Min(1)
	@Max(5)
	private Integer rating;

	@NotNull
	private ProductDTO product;

	@NotNull
	private UserDTO user;

	public ReviewDTO() {
		super();
	}

	public ReviewDTO(Long id, @NotBlank String comment, @NotNull @Min(1) @Max(5) Integer rating,
			@NotNull ProductDTO product, @NotNull UserDTO user) {
		super();
		this.id = id;
		this.comment = comment;
		this.rating = rating;
		this.product = product;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}

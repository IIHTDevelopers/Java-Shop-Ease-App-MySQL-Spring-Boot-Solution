package com.shopease.service;

import java.util.List;

import com.shopease.dto.ProductDTO;
import com.shopease.dto.ReviewDTO;

public interface ProductService {
	ProductDTO addProduct(ProductDTO productDTO);

	List<ProductDTO> getAllProducts();

	ProductDTO getProductById(Long id);

	ProductDTO updateProduct(Long id, ProductDTO productDTO);

	Boolean deleteProduct(Long id);

	List<ReviewDTO> getProductReviews(Long productId);

	ReviewDTO addProductReview(Long productId, ReviewDTO reviewDTO);

	List<ProductDTO> searchProducts(String criteria);
}

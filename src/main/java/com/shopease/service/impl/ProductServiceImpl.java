package com.shopease.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopease.dto.InventoryDTO;
import com.shopease.dto.ProductDTO;
import com.shopease.dto.ReviewDTO;
import com.shopease.dto.UserDTO;
import com.shopease.entity.Inventory;
import com.shopease.entity.Product;
import com.shopease.entity.Review;
import com.shopease.entity.User;
import com.shopease.exception.NotFoundException;
import com.shopease.repo.ProductRepository;
import com.shopease.repo.ReviewRepository;
import com.shopease.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public ProductDTO addProduct(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());

		Product savedProduct = productRepository.save(product);
		productDTO.setId(savedProduct.getId());

		return productDTO;
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public ProductDTO getProductById(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Product not found for this id : " + id));
		return convertToDTO(product);
	}

	@Override
	public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Product not found for this id : " + id));

		product.setName(productDTO.getName());
		product.setDescription(productDTO.getDescription());
		product.setPrice(productDTO.getPrice());

		Product updatedProduct = productRepository.save(product);
		return convertToDTO(updatedProduct);
	}

	@Override
	public Boolean deleteProduct(Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Product not found for this id : " + id));

		productRepository.delete(product);
		return true;
	}

	@Override
	public List<ReviewDTO> getProductReviews(Long productId) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found for this id : " + productId));

		return product.getReviews().stream().map(this::convertReviewToDTO).collect(Collectors.toList());
	}

	@Override
	public ReviewDTO addProductReview(Long productId, ReviewDTO reviewDTO) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found for this id : " + productId));

		Review review = new Review();
		review.setComment(reviewDTO.getComment());
		review.setRating(reviewDTO.getRating());
		review.setProduct(product);

		Review savedReview = reviewRepository.save(review);
		reviewDTO.setId(savedReview.getId());

		return reviewDTO;
	}

	@Override
	public List<ProductDTO> searchProducts(String criteria) {
		return productRepository.findByNameContaining(criteria).stream().map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	private ProductDTO convertToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());

		if (product.getReviews() != null) {
			List<ReviewDTO> reviewDTOs = product.getReviews().stream().map(this::convertReviewToDTO)
					.collect(Collectors.toList());
			productDTO.setReviews(reviewDTOs);
		}

		if (product.getInventory() != null) {
			InventoryDTO inventoryDTO = convertInventoryToDTO(product.getInventory());
			productDTO.setInventory(inventoryDTO);
		}

		return productDTO;
	}

	private ReviewDTO convertReviewToDTO(Review review) {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setId(review.getId());
		reviewDTO.setComment(review.getComment());
		reviewDTO.setRating(review.getRating());

		if (review.getUser() != null) {
			UserDTO userDTO = convertUserToDTO(review.getUser());
			reviewDTO.setUser(userDTO);
		}

		return reviewDTO;
	}

	private UserDTO convertUserToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());

		return userDTO;
	}

	private InventoryDTO convertInventoryToDTO(Inventory inventory) {
		InventoryDTO inventoryDTO = new InventoryDTO();
		inventoryDTO.setId(inventory.getId());
		inventoryDTO.setLocation(inventory.getLocation());
		inventoryDTO.setStockQuantity(inventory.getStockQuantity());

		return inventoryDTO;
	}
}

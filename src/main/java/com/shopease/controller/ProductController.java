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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopease.dto.ProductDTO;
import com.shopease.dto.ReviewDTO;
import com.shopease.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO productDTO) {
		ProductDTO createdProduct = productService.addProduct(productDTO);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<ProductDTO> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
		ProductDTO productDTO = productService.getProductById(id);
		return new ResponseEntity<>(productDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDTO productDTO) {
		ProductDTO updatedProduct = productService.updateProduct(id, productDTO);
		return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}/reviews")
	public ResponseEntity<List<ReviewDTO>> getProductReviews(@PathVariable Long id) {
		List<ReviewDTO> reviews = productService.getProductReviews(id);
		return new ResponseEntity<>(reviews, HttpStatus.OK);
	}

	@PostMapping("/{id}/reviews")
	public ResponseEntity<ReviewDTO> addProductReview(@PathVariable Long id, @RequestBody @Valid ReviewDTO reviewDTO) {
		ReviewDTO createdReview = productService.addProductReview(id, reviewDTO);
		return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
	}

	@GetMapping("/search")
	public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String criteria) {
		List<ProductDTO> products = productService.searchProducts(criteria);
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
}

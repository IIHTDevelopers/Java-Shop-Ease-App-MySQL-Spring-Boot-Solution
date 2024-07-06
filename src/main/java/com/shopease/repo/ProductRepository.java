package com.shopease.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopease.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByNameContaining(String name);

	@Query("SELECT p FROM Product p WHERE p.price < ?1")
	List<Product> findProductsCheaperThan(Double price);
}

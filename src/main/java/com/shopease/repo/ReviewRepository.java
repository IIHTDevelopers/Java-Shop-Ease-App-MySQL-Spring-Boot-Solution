package com.shopease.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopease.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
	// Custom query methods can be added here if needed
}

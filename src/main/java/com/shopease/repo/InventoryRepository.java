package com.shopease.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopease.entity.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	List<Inventory> findByLocation(String location);

	@Query("SELECT i FROM Inventory i WHERE i.stockQuantity < ?1")
	List<Inventory> findLowStockItems(Integer stockThreshold);
}

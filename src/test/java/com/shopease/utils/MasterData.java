package com.shopease.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shopease.dto.InventoryDTO;
import com.shopease.dto.OrderDTO;
import com.shopease.dto.ProductDTO;
import com.shopease.dto.ReviewDTO;
import com.shopease.dto.UserDTO;

public class MasterData {

	public static UserDTO getUserDTO() {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(1L);
		userDTO.setUsername("john_doe");
		userDTO.setEmail("john.doe@example.com");
		userDTO.setPassword("password123");
		userDTO.setFirstName("John");
		userDTO.setLastName("Doe");
		return userDTO;
	}

	public static List<UserDTO> getUserDTOList() {
		List<UserDTO> userDTOList = new ArrayList<>();
		userDTOList.add(getUserDTO());
		return userDTOList;
	}

	public static ProductDTO getProductDTO() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Laptop");
		productDTO.setDescription("High performance laptop");
		productDTO.setPrice(1200.00);
		return productDTO;
	}

	public static List<ProductDTO> getProductDTOList() {
		List<ProductDTO> productDTOList = new ArrayList<>();
		productDTOList.add(getProductDTO());
		return productDTOList;
	}

	public static OrderDTO getOrderDTO() {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(1L);
		orderDTO.setOrderDate(new Date());
		orderDTO.setStatus("CONFIRMED");
		orderDTO.setUser(getUserDTO());
		orderDTO.setProduct(getProductDTO());
		return orderDTO;
	}

	public static List<OrderDTO> getOrderDTOList() {
		List<OrderDTO> orderDTOList = new ArrayList<>();
		orderDTOList.add(getOrderDTO());
		return orderDTOList;
	}

	public static InventoryDTO getInventoryDTO() {
		InventoryDTO inventoryDTO = new InventoryDTO();
		inventoryDTO.setId(1L);
		inventoryDTO.setLocation("Warehouse 1");
		inventoryDTO.setStockQuantity(100);
		return inventoryDTO;
	}

	public static List<InventoryDTO> getInventoryDTOList() {
		List<InventoryDTO> inventoryDTOList = new ArrayList<>();
		inventoryDTOList.add(getInventoryDTO());
		return inventoryDTOList;
	}

	public static ReviewDTO getReviewDTO() {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setId(1L);
		reviewDTO.setComment("Excellent product!");
		reviewDTO.setRating(5);
		reviewDTO.setUser(getUserDTO());
		reviewDTO.setProduct(getProductDTO());
		return reviewDTO;
	}

	public static List<ReviewDTO> getReviewDTOList() {
		List<ReviewDTO> reviewDTOList = new ArrayList<>();
		reviewDTOList.add(getReviewDTO());
		return reviewDTOList;
	}

	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String randomStringWithSize(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append("A");
		}
		return s.toString();
	}
}

package com.shopease.service;

import java.util.List;

import com.shopease.dto.OrderDTO;

public interface OrderService {
	OrderDTO createOrder(OrderDTO orderDTO);

	List<OrderDTO> getAllOrders();

	OrderDTO getOrderById(Long id);

	OrderDTO updateOrder(Long id, OrderDTO orderDTO);

	Boolean cancelOrder(Long id);

	String getOrderStatus(Long id);

	Boolean updateOrderStatus(Long id, String status);

	Boolean initiateReturn(Long id);
}

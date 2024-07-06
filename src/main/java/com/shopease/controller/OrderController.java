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

import com.shopease.dto.OrderDTO;
import com.shopease.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@PostMapping
	public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO orderDTO) {
		OrderDTO createdOrder = orderService.createOrder(orderDTO);
		return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<OrderDTO>> getAllOrders() {
		List<OrderDTO> orders = orderService.getAllOrders();
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
		OrderDTO orderDTO = orderService.getOrderById(id);
		return new ResponseEntity<>(orderDTO, HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) {
		OrderDTO updatedOrder = orderService.updateOrder(id, orderDTO);
		return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> cancelOrder(@PathVariable Long id) {
		orderService.cancelOrder(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}/status")
	public ResponseEntity<String> getOrderStatus(@PathVariable Long id) {
		String status = orderService.getOrderStatus(id);
		return new ResponseEntity<>(status, HttpStatus.OK);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Boolean> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
		Boolean result = orderService.updateOrderStatus(id, status);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/{id}/return")
	public ResponseEntity<Boolean> initiateReturn(@PathVariable Long id) {
		Boolean result = orderService.initiateReturn(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}

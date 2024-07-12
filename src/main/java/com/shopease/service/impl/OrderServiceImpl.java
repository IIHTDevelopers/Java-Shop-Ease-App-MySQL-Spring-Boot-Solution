package com.shopease.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopease.dto.OrderDTO;
import com.shopease.dto.ProductDTO;
import com.shopease.dto.UserDTO;
import com.shopease.entity.Order;
import com.shopease.entity.Product;
import com.shopease.entity.User;
import com.shopease.exception.NotFoundException;
import com.shopease.repo.OrderRepository;
import com.shopease.repo.ProductRepository;
import com.shopease.repo.UserRepository;
import com.shopease.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Override
	public OrderDTO createOrder(OrderDTO orderDTO) {
		Order order = new Order();
		order.setOrderDate(orderDTO.getOrderDate());
		order.setStatus(orderDTO.getStatus());

		User user = userRepository.findById(orderDTO.getUser().getId()).orElseThrow(
				() -> new NotFoundException("User not found for this id : " + orderDTO.getUser().getId()));
		order.setUser(user);

		Product product = productRepository.findById(orderDTO.getProduct().getId()).orElseThrow(
				() -> new NotFoundException("Product not found for this id : " + orderDTO.getProduct().getId()));
		order.setProduct(product);

		Order savedOrder = orderRepository.save(order);
		orderDTO.setId(savedOrder.getId());

		return orderDTO;
	}

	@Override
	public List<OrderDTO> getAllOrders() {
		return orderRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	@Override
	public OrderDTO getOrderById(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Order not found for this id : " + id));
		return convertToDTO(order);
	}

	@Override
	public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Order not found for this id : " + id));

		order.setOrderDate(orderDTO.getOrderDate());
		order.setStatus(orderDTO.getStatus());

		User user = userRepository.findById(orderDTO.getUser().getId()).orElseThrow(
				() -> new NotFoundException("User not found for this id : " + orderDTO.getUser().getId()));
		order.setUser(user);

		Product product = productRepository.findById(orderDTO.getProduct().getId()).orElseThrow(
				() -> new NotFoundException("Product not found for this id : " + orderDTO.getProduct().getId()));
		order.setProduct(product);

		Order updatedOrder = orderRepository.save(order);
		return convertToDTO(updatedOrder);
	}

	@Override
	public Boolean cancelOrder(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Order not found for this id : " + id));

		orderRepository.delete(order);
		return true;
	}

	@Override
	public String getOrderStatus(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Order not found for this id : " + id));

		return order.getStatus();
	}

	@Override
	public Boolean updateOrderStatus(Long id, String status) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Order not found for this id : " + id));

		order.setStatus(status);
		orderRepository.save(order);
		return true;
	}

	@Override
	public Boolean initiateReturn(Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("Order not found for this id : " + id));

		order.setStatus("RETURN_INITIATED");
		orderRepository.save(order);
		return true;
	}

	private OrderDTO convertToDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setOrderDate(order.getOrderDate());
		orderDTO.setStatus(order.getStatus());

		User user = order.getUser();
		if (user != null) {
			orderDTO.setUser(convertUserToDTO(user));
		}

		Product product = order.getProduct();
		if (product != null) {
			orderDTO.setProduct(convertProductToDTO(product));
		}

		return orderDTO;
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

	private ProductDTO convertProductToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setDescription(product.getDescription());
		productDTO.setPrice(product.getPrice());

		return productDTO;
	}
}

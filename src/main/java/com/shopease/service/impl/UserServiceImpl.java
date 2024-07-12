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
import com.shopease.repo.UserRepository;
import com.shopease.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public UserDTO register(UserDTO userDTO) {
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());

		User savedUser = userRepository.save(user);
		userDTO.setId(savedUser.getId());

		return userDTO;
	}

	@Override
	public UserDTO getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found for this id : " + id));
		return convertToDTO(user);
	}

	@Override
	public UserDTO updateUser(Long id, UserDTO userDTO) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found for this id : " + id));

		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());

		User updatedUser = userRepository.save(user);
		return convertToDTO(updatedUser);
	}

	@Override
	public Boolean deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("User not found for this id : " + id));

		userRepository.delete(user);
		return true;
	}

	@Override
	public List<OrderDTO> getUserOrders(Long userId) {
		List<Order> orders = orderRepository.findByUserId(userId);
		return orders.stream().map(this::convertOrderToDTO).collect(Collectors.toList());
	}

	private UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setUsername(user.getUsername());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		return userDTO;
	}

	private OrderDTO convertOrderToDTO(Order order) {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		orderDTO.setOrderDate(order.getOrderDate());
		orderDTO.setStatus(order.getStatus());

		if (order.getUser() != null) {
			orderDTO.setUser(convertToDTO(order.getUser()));
		}

		if (order.getProduct() != null) {
			orderDTO.setProduct(convertProductToDTO(order.getProduct()));
		}

		return orderDTO;
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

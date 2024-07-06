package com.shopease.service;

import java.util.List;

import com.shopease.dto.OrderDTO;
import com.shopease.dto.UserDTO;

public interface UserService {
	UserDTO register(UserDTO userDTO);

	UserDTO getUserById(Long id);

	UserDTO updateUser(Long id, UserDTO userDTO);

	Boolean deleteUser(Long id);

	List<OrderDTO> getUserOrders(Long userId);
}

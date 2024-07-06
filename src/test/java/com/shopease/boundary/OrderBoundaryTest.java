package com.shopease.boundary;

import static com.shopease.utils.TestUtils.boundaryTestFile;
import static com.shopease.utils.TestUtils.currentTest;
import static com.shopease.utils.TestUtils.testReport;
import static com.shopease.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.shopease.dto.OrderDTO;
import com.shopease.dto.ProductDTO;
import com.shopease.dto.UserDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class OrderBoundaryTest {

	private static Validator validator;

	@BeforeAll
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testUserNotNull() throws IOException {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUser(null);
		orderDTO.setProduct(new ProductDTO());
		orderDTO.setOrderDate(new Date());
		orderDTO.setStatus("CONFIRMED");
		Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testProductNotNull() throws IOException {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUser(new UserDTO());
		orderDTO.setProduct(null);
		orderDTO.setOrderDate(new Date());
		orderDTO.setStatus("CONFIRMED");
		Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testOrderDateNotNull() throws IOException {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUser(new UserDTO());
		orderDTO.setProduct(new ProductDTO());
		orderDTO.setOrderDate(null);
		orderDTO.setStatus("CONFIRMED");
		Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testStatusNotBlank() throws IOException {
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setUser(new UserDTO());
		orderDTO.setProduct(new ProductDTO());
		orderDTO.setOrderDate(new Date());
		orderDTO.setStatus("");
		Set<ConstraintViolation<OrderDTO>> violations = validator.validate(orderDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}

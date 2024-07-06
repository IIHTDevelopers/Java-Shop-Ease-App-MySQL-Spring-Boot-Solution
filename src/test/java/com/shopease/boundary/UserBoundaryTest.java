package com.shopease.boundary;

import static com.shopease.utils.TestUtils.boundaryTestFile;
import static com.shopease.utils.TestUtils.currentTest;
import static com.shopease.utils.TestUtils.testReport;
import static com.shopease.utils.TestUtils.yakshaAssert;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.shopease.dto.UserDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class UserBoundaryTest {

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
	public void testUsernameNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testUsernameSize() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername("A"); // Less than min size of 3
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testPasswordNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword("");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testPasswordSize() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setPassword("12345"); // Less than min size of 6
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEmailNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testEmailFormat() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("invalid-email");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testFirstNameNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName("");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testLastNameNotBlank() throws IOException {
		UserDTO userDTO = new UserDTO();
		userDTO.setLastName("");
		Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}

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

import com.shopease.dto.InventoryDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class InventoryBoundaryTest {

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
	public void testLocationNotBlank() throws IOException {
		InventoryDTO inventoryDTO = new InventoryDTO();
		inventoryDTO.setLocation("");
		Set<ConstraintViolation<InventoryDTO>> violations = validator.validate(inventoryDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testLocationNotNull() throws IOException {
		InventoryDTO inventoryDTO = new InventoryDTO();
		inventoryDTO.setLocation(null);
		Set<ConstraintViolation<InventoryDTO>> violations = validator.validate(inventoryDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testStockQuantityNotNull() throws IOException {
		InventoryDTO inventoryDTO = new InventoryDTO();
		inventoryDTO.setStockQuantity(null);
		Set<ConstraintViolation<InventoryDTO>> violations = validator.validate(inventoryDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}

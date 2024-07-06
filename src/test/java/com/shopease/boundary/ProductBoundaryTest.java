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

import com.shopease.dto.ProductDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class ProductBoundaryTest {

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
	public void testNameNotBlank() throws IOException {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("");
		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testNameSize() throws IOException {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName("A".repeat(101)); // Exceeding the max size of 100
		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testDescriptionSize() throws IOException {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setDescription("A".repeat(2001)); // Exceeding the max size of 2000
		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}

	@Test
	public void testPriceNotNull() throws IOException {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setPrice(null);
		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
		try {
			yakshaAssert(currentTest(), !violations.isEmpty(), boundaryTestFile);
		} catch (Exception e) {
			yakshaAssert(currentTest(), false, boundaryTestFile);
		}
	}
}

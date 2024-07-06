package com.shopease.exception;

import static com.shopease.utils.TestUtils.currentTest;
import static com.shopease.utils.TestUtils.exceptionTestFile;
import static com.shopease.utils.TestUtils.testReport;
import static com.shopease.utils.TestUtils.yakshaAssert;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.shopease.controller.ProductController;
import com.shopease.dto.ProductDTO;
import com.shopease.service.ProductService;
import com.shopease.utils.MasterData;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testGetProductByIdNotFoundException() throws Exception {
		Long productId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Product not found");

		when(this.productService.getProductById(productId)).thenThrow(new NotFoundException("Product not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/" + productId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testAddProductInvalidDataException() throws Exception {
		ProductDTO productDTO = new ProductDTO(); // Create an invalid ProductDTO

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/products")
				.content(MasterData.asJsonString(productDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testDeleteProductNotFoundException() throws Exception {
		Long productId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Product not found");

		when(this.productService.deleteProduct(productId)).thenThrow(new NotFoundException("Product not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/" + productId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testGetProductReviewsNotFoundException() throws Exception {
		Long productId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Product not found");

		when(this.productService.getProductReviews(productId)).thenThrow(new NotFoundException("Product not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/" + productId + "/reviews")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

}

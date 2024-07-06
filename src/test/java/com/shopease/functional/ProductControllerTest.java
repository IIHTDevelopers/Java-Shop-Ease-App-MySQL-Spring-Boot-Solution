package com.shopease.functional;

import static com.shopease.utils.MasterData.getProductDTO;
import static com.shopease.utils.MasterData.getProductDTOList;
import static com.shopease.utils.MasterData.getReviewDTO;
import static com.shopease.utils.MasterData.getReviewDTOList;
import static com.shopease.utils.TestUtils.businessTestFile;
import static com.shopease.utils.TestUtils.currentTest;
import static com.shopease.utils.TestUtils.testReport;
import static com.shopease.utils.TestUtils.yakshaAssert;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.shopease.controller.ProductController;
import com.shopease.dto.ProductDTO;
import com.shopease.dto.ReviewDTO;
import com.shopease.service.ProductService;
import com.shopease.utils.MasterData;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
public class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddProduct() throws Exception {
		ProductDTO productDTO = getProductDTO();

		when(productService.addProduct(any())).thenReturn(productDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/products")
				.content(MasterData.asJsonString(productDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(productDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testGetAllProducts() throws Exception {
		List<ProductDTO> productDTOList = getProductDTOList();

		when(productService.getAllProducts()).thenReturn(productDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(productDTOList))
						? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testGetProductById() throws Exception {
		ProductDTO productDTO = getProductDTO();
		Long productId = productDTO.getId();

		when(productService.getProductById(productId)).thenReturn(productDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/" + productId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(productDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testUpdateProduct() throws Exception {
		ProductDTO productDTO = getProductDTO();
		Long productId = productDTO.getId();

		when(productService.updateProduct(any(), any())).thenReturn(productDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/products/" + productId)
				.content(MasterData.asJsonString(productDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(productDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testDeleteProduct() throws Exception {
		Long productId = 1L;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/products/" + productId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetProductReviews() throws Exception {
		List<ReviewDTO> reviewDTOList = getReviewDTOList();
		Long productId = 1L;

		when(productService.getProductReviews(productId)).thenReturn(reviewDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/" + productId + "/reviews")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(reviewDTOList)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testAddProductReview() throws Exception {
		ReviewDTO reviewDTO = getReviewDTO();
		Long productId = 1L;

		when(productService.addProductReview(any(), any())).thenReturn(reviewDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/products/" + productId + "/reviews")
				.content(MasterData.asJsonString(reviewDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(reviewDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testSearchProducts() throws Exception {
		List<ProductDTO> productDTOList = getProductDTOList();
		String criteria = "Laptop";

		when(productService.searchProducts(criteria)).thenReturn(productDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/products/search").param("criteria", criteria)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(productDTOList))
						? "true"
						: "false",
				businessTestFile);
	}
}

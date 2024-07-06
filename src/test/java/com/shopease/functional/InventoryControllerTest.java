package com.shopease.functional;

import static com.shopease.utils.MasterData.getInventoryDTO;
import static com.shopease.utils.MasterData.getInventoryDTOList;
import static com.shopease.utils.MasterData.getProductDTOList;
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

import com.shopease.controller.InventoryController;
import com.shopease.dto.InventoryDTO;
import com.shopease.dto.ProductDTO;
import com.shopease.service.InventoryService;
import com.shopease.utils.MasterData;

@WebMvcTest(InventoryController.class)
@AutoConfigureMockMvc
public class InventoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InventoryService inventoryService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testAddInventoryItem() throws Exception {
		InventoryDTO inventoryDTO = getInventoryDTO();

		when(inventoryService.addInventoryItem(any())).thenReturn(inventoryDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/inventory")
				.content(MasterData.asJsonString(inventoryDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(inventoryDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testGetAllInventoryItems() throws Exception {
		List<InventoryDTO> inventoryDTOList = getInventoryDTOList();

		when(inventoryService.getAllInventoryItems()).thenReturn(inventoryDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/inventory")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(inventoryDTOList))
						? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testGetInventoryItemById() throws Exception {
		InventoryDTO inventoryDTO = getInventoryDTO();
		Long inventoryId = inventoryDTO.getId();

		when(inventoryService.getInventoryItemById(inventoryId)).thenReturn(inventoryDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/inventory/" + inventoryId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(inventoryDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testUpdateInventoryItem() throws Exception {
		InventoryDTO inventoryDTO = getInventoryDTO();
		Long inventoryId = inventoryDTO.getId();

		when(inventoryService.updateInventoryItem(any(), any())).thenReturn(inventoryDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/inventory/" + inventoryId)
				.content(MasterData.asJsonString(inventoryDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(inventoryDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testDeleteInventoryItem() throws Exception {
		Long inventoryId = 1L;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/inventory/" + inventoryId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetInventoryStatusOfProducts() throws Exception {
		List<ProductDTO> productDTOList = getProductDTOList();

		when(inventoryService.getInventoryStatusOfProducts()).thenReturn(productDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/inventory/products")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(productDTOList))
						? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testUpdateProductInventory() throws Exception {
		InventoryDTO inventoryDTO = getInventoryDTO();
		Long productId = inventoryDTO.getId();

		when(inventoryService.updateProductInventory(any(), any())).thenReturn(inventoryDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/inventory/products/" + productId)
				.content(MasterData.asJsonString(inventoryDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(inventoryDTO)) ? "true"
						: "false",
				businessTestFile);
	}
}

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

import com.shopease.controller.InventoryController;
import com.shopease.dto.InventoryDTO;
import com.shopease.service.InventoryService;
import com.shopease.utils.MasterData;

@WebMvcTest(InventoryController.class)
@AutoConfigureMockMvc
public class InventoryExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private InventoryService inventoryService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testGetInventoryItemByIdNotFoundException() throws Exception {
		Long inventoryId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Inventory item not found");

		when(this.inventoryService.getInventoryItemById(inventoryId))
				.thenThrow(new NotFoundException("Inventory item not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/inventory/" + inventoryId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testAddInventoryItemInvalidDataException() throws Exception {
		InventoryDTO inventoryDTO = new InventoryDTO(); // Create an invalid InventoryDTO

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/inventory")
				.content(MasterData.asJsonString(inventoryDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testDeleteInventoryItemNotFoundException() throws Exception {
		Long inventoryId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Inventory item not found");

		when(this.inventoryService.deleteInventoryItem(inventoryId))
				.thenThrow(new NotFoundException("Inventory item not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/inventory/" + inventoryId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateInventoryItemNotFoundException() throws Exception {
		Long inventoryId = 1L;
		InventoryDTO inventoryDTO = MasterData.getInventoryDTO();
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Inventory item not found");

		when(this.inventoryService.updateInventoryItem(inventoryId, inventoryDTO))
				.thenThrow(new NotFoundException("Inventory item not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/inventory/" + inventoryId)
				.content(MasterData.asJsonString(inventoryDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}
}

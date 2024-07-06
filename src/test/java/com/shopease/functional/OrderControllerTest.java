package com.shopease.functional;

import static com.shopease.utils.MasterData.getOrderDTO;
import static com.shopease.utils.MasterData.getOrderDTOList;
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

import com.shopease.controller.OrderController;
import com.shopease.dto.OrderDTO;
import com.shopease.service.OrderService;
import com.shopease.utils.MasterData;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testCreateOrder() throws Exception {
		OrderDTO orderDTO = getOrderDTO();

		when(orderService.createOrder(any())).thenReturn(orderDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/orders")
				.content(MasterData.asJsonString(orderDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(orderDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testGetAllOrders() throws Exception {
		List<OrderDTO> orderDTOList = getOrderDTOList();

		when(orderService.getAllOrders()).thenReturn(orderDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/orders")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(orderDTOList)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testGetOrderById() throws Exception {
		OrderDTO orderDTO = getOrderDTO();
		Long orderId = orderDTO.getId();

		when(orderService.getOrderById(orderId)).thenReturn(orderDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/orders/" + orderId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(orderDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testUpdateOrder() throws Exception {
		OrderDTO orderDTO = getOrderDTO();
		Long orderId = orderDTO.getId();

		when(orderService.updateOrder(any(), any())).thenReturn(orderDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/orders/" + orderId)
				.content(MasterData.asJsonString(orderDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(orderDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testCancelOrder() throws Exception {
		Long orderId = 1L;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/orders/" + orderId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetOrderStatus() throws Exception {
		OrderDTO orderDTO = getOrderDTO();
		Long orderId = orderDTO.getId();
		String status = orderDTO.getStatus();

		when(orderService.getOrderStatus(orderId)).thenReturn(status);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/orders/" + orderId + "/status")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contentEquals(status) ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testUpdateOrderStatus() throws Exception {
		OrderDTO orderDTO = getOrderDTO();
		Long orderId = orderDTO.getId();
		String status = "SHIPPED";

		when(orderService.updateOrderStatus(orderId, status)).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/orders/" + orderId + "/status")
				.param("status", status).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contentEquals("true") ? "true" : "false",
				businessTestFile);
	}

	@Test
	public void testInitiateReturn() throws Exception {
		OrderDTO orderDTO = getOrderDTO();
		Long orderId = orderDTO.getId();

		when(orderService.initiateReturn(orderId)).thenReturn(true);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/orders/" + orderId + "/return")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), result.getResponse().getContentAsString().contentEquals("true") ? "true" : "false",
				businessTestFile);
	}
}

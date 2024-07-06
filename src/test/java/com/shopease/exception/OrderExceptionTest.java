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

import com.shopease.controller.OrderController;
import com.shopease.dto.OrderDTO;
import com.shopease.service.OrderService;
import com.shopease.utils.MasterData;

@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc
public class OrderExceptionTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OrderService orderService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testGetOrderByIdNotFoundException() throws Exception {
		Long orderId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Order not found");

		when(this.orderService.getOrderById(orderId)).thenThrow(new NotFoundException("Order not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/orders/" + orderId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testCreateOrderInvalidDataException() throws Exception {
		OrderDTO orderDTO = new OrderDTO(); // Create an invalid OrderDTO

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/orders")
				.content(MasterData.asJsonString(orderDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getStatus() == HttpStatus.BAD_REQUEST.value() ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testCancelOrderNotFoundException() throws Exception {
		Long orderId = 1L;
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Order not found");

		when(this.orderService.cancelOrder(orderId)).thenThrow(new NotFoundException("Order not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/orders/" + orderId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}

	@Test
	public void testUpdateOrderStatusNotFoundException() throws Exception {
		Long orderId = 1L;
		String status = "SHIPPED";
		ErrorResponse exResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Order not found");

		when(this.orderService.updateOrderStatus(orderId, status)).thenThrow(new NotFoundException("Order not found"));
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/orders/" + orderId + "/status")
				.param("status", status).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		yakshaAssert(currentTest(),
				(result.getResponse().getContentAsString().contains(exResponse.getMessage()) ? "true" : "false"),
				exceptionTestFile);
	}
}

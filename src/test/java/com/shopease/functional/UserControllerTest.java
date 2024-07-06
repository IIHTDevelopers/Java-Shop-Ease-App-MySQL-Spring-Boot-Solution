package com.shopease.functional;

import static com.shopease.utils.MasterData.getOrderDTOList;
import static com.shopease.utils.MasterData.getUserDTO;
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

import com.shopease.controller.UserController;
import com.shopease.dto.OrderDTO;
import com.shopease.dto.UserDTO;
import com.shopease.service.UserService;
import com.shopease.utils.MasterData;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;

	@AfterAll
	public static void afterAll() {
		testReport();
	}

	@Test
	public void testRegister() throws Exception {
		UserDTO userDTO = getUserDTO();

		when(userService.register(any())).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/users/register")
				.content(MasterData.asJsonString(userDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(userDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testGetUserById() throws Exception {
		UserDTO userDTO = getUserDTO();
		Long userId = userDTO.getId();

		when(userService.getUserById(userId)).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/" + userId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(userDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testUpdateUser() throws Exception {
		UserDTO userDTO = getUserDTO();
		Long userId = userDTO.getId();

		when(userService.updateUser(any(), any())).thenReturn(userDTO);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/users/" + userId)
				.content(MasterData.asJsonString(userDTO)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(userDTO)) ? "true"
						: "false",
				businessTestFile);
	}

	@Test
	public void testDeleteUser() throws Exception {
		Long userId = 1L;
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/users/" + userId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(), (result.getResponse().getContentAsString().contentEquals("") ? "true" : "false"),
				businessTestFile);
	}

	@Test
	public void testGetUserOrders() throws Exception {
		List<OrderDTO> orderDTOList = getOrderDTOList();
		Long userId = 1L;

		when(userService.getUserOrders(userId)).thenReturn(orderDTOList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/" + userId + "/orders")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		yakshaAssert(currentTest(),
				result.getResponse().getContentAsString().contentEquals(MasterData.asJsonString(orderDTOList)) ? "true"
						: "false",
				businessTestFile);
	}
}

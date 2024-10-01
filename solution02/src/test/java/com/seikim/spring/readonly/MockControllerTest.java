package com.seikim.spring.readonly;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
public abstract class MockControllerTest {
	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	protected String convertToJson(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new AssertionError("올바르지 않는 JSON 형식입니다.");
		}
	}

	protected <T> T convertToObject(String json, Class<T> clazz) throws JsonProcessingException {
		return objectMapper.readValue(json, clazz);
	}
}

package com.seikim.spring;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.seikim.spring.readonly.MemberFixture;
import com.seikim.spring.readonly.MockControllerTest;
import com.seikim.spring.readonly.data.response.MemberSaveResponse;
import com.seikim.spring.readonly.domain.Log;
import com.seikim.spring.readonly.repository.LogRepository;
import com.seikim.spring.readonly.service.MemberService;

class SolutionTest extends MockControllerTest {

	private static final Logger log = LoggerFactory.getLogger(SolutionTest.class);
	@Autowired
	private MemberService memberService;

	@Autowired
	private LogRepository logRepository;

	@AfterEach
	public void after() {
		logRepository.clear();
	}

	/**
	 * API가 요청된다면 반드시 로그를 저장해야합니다.</br>
	 * 로그 객체의 구조는 {@link com.seikim.spring.readonly.domain.Log}에서 확인할 수 있습니다.
	 */
	@DisplayName("사용자의 요청이 실행되면 로그가 저장된다.")
	@ParameterizedTest
	@EnumSource(MemberFixture.class)
	void saveRequestLogTest(MemberFixture fixture) throws Exception {
		/* Given */
		String body = convertToJson(fixture.toSaveRequest());

		/* When */
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/member")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andDo(MockMvcResultHandlers.print());

		MemberSaveResponse response = convertToObject(resultActions.andReturn().getResponse().getContentAsString(),
				MemberSaveResponse.class);

		/* Then */
		resultActions.andExpect(MockMvcResultMatchers.status().isCreated());
		Assertions.assertAll(
				() -> assertThat(response.memberId()).isEqualTo(fixture.getId()),
				() -> assertThat(response.accountAmount()).isEqualTo(fixture.getAmount())
		);

		List<Log> findLogs = logRepository.findAll();
		log.info(findLogs.toString());

		assertThat(findLogs).isNotEmpty(); /* 해당 테스트가 성공해야 합니다. */
	}

	@DisplayName("사용자의 회원가입은 1초 미만으로 나올 수 없다.")
	@Test
	void validLogTimerTest() throws Exception {
		/* Given */
		MemberFixture fixture = MemberFixture.KIM;
		String body = convertToJson(fixture.toSaveRequest());
		int nTreads = 25;
		ExecutorService executorService = Executors.newFixedThreadPool(nTreads);

		/* When */
		for (int i = 0; i < nTreads; i++) {
			executorService.submit(() -> {
				try {
					mockMvc.perform(RestDocumentationRequestBuilders.post("/member")
									.contentType(MediaType.APPLICATION_JSON)
									.content(body))
							.andDo(MockMvcResultHandlers.print());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
		}

		executorService.shutdown();
		if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
			executorService.shutdownNow();
		}

		/* Then */
		List<Log> findLogs = logRepository.findAll();
		assertThat(findLogs).isNotEmpty();
		findLogs.forEach(findLog -> assertThat(findLog.getElapsedTime()).isGreaterThan(1_000L)); /* 해당 테스트가 성공해야 합니다. */
	}

	@DisplayName("Response Header에는 수행 시간이 포함되어야 한다.")
	@Test
	void validateResponseHeaderTest() throws Exception {
		/* Given */
		MemberFixture fixture = MemberFixture.KIM;
		String body = convertToJson(fixture.toSaveRequest());

		/* When */
		ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/member")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andDo(MockMvcResultHandlers.print());

		String elapsedTime = resultActions.andReturn().getResponse().getHeader("Elapsed-Time");

		/* Then */
		assertThat(elapsedTime).isNotNull(); /* 해당 테스트가 성공해야 합니다. */
	}
}

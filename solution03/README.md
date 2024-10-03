# HTTP 요청에 로그를 남기고 데이터베이스 저장해보자!

## 개요

어느날, 김세이라는 이름의 열정적인 개발자는 프로젝트를 진행하다가 문득 생각에 잠겼다.

“이 모든 요청들이 어떻게 처리되고 있는지, 그리고 어떤 데이터들이 오가는지 로그로 남겨서 저장할 수 있다면 정말 좋을 텐데…”

그는 사용자들이 애플리케이션에 보내는 요청을 하나도 빠짐없이 기록해두고 싶었다. 이러한 기록은 나중에 문제를 분석하거나 성능 개선을 위해 꼭 필요한 데이터가 될 것이 분명했다. 하지만 곧 깨달았다. Spring에서
기본적으로 제공하지 않는 기능이었다.

김세이는 고민 끝에 주변 동료들과 상의하기로 마음먹었다. 팀 동료들에게 이야기를 전하자, 모두들 그의 생각에 동의했다.

“좋은 생각이야, 세이! 로그가 쌓이면 나중에 트러블슈팅할 때도 도움이 될 거야!” 동료 개발자 중 한 명이 고개를 끄덕이며 말했다.

“맞아, 그리고 성능을 추적하거나 분석할 때도 매우 유용하지,” 또 다른 동료도 공감했다.

그리하여 김세이를 비롯한 팀원들은 힘을 합쳐 이 기능을 구현하기로 결심했다.

> Author: Chat-GPT

## 문제

> `com.seikim.spring.readonly` 파일은 오로직 읽기만하시고 수정하지 마세요!

`com.seikim.spring.solution` 패키지 안에 있는 파일만 수정해서 문제를 해결해보세요!

테스트는 `com.seikim.spring.SolutionTest`을 실행시켜 모두다 성공시키면 성공입니다!

### 1번 문제

```java

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

	assertThat(logRepository.findAll()).isNotEmpty(); /* 해당 테스트가 성공해야 합니다. */
}
```

해당 문제는 API 요청이 들어올 때 로그가 저장되는지 확인하는 상황입니다. 이 테스트는 사용자가 요청을 보냈을 때, 그 요청에 대한 로그가 올바르게 기록되는지를 검증하는 것을 목표로 합니다.

### 2번 문제

```java

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
```

해당 문제는 스프링에서 발생할 수 있는 동시성 문제를 해결하는 상황입니다. 현재 회원 한 명을 저장하는 데 약 2초 이상의 시간이 소요되고 있으며, 여러 명이 동시에 회원을 생성할 때 소요 시간을 정확하게 계산하는
데 어려움이 있습니다.

이 테스트의 목표는 여러 개의 요청이 동시에 처리되는 상황에서 발생할 수 있는 동시성 문제를 해결할 수 있는 역량을 평가하는 것입니다.

### 3번 문제

```java

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
```

3번 문제는 HTTP 헤더에 값을 추가할 수 있는 역량을 확인하는 문제입니다.

이 테스트에서는 API 요청을 처리한 후, 응답 헤더에 ‘Elapsed-Time’이라는 값을 포함시켜 응답 시간 정보를 전달할 수 있는지를 검증합니다. 이를 통해 서버가 요청 처리 시간을 계산하고, 그 값을 헤더에
추가해 클라이언트로 전달할 수 있는 능력을 평가합니다.

### Log

```java

@ToString(callSuper = true)
@Getter
public class Log extends UUIDEntity {

	private final LocalDateTime requestTime;    // API 요청 시간
	private final long elapsedTime;    // API 경과 시간
	private final String errorMessage;

	public Log(long elapsedTime) {
		super(null);
		this.requestTime = LocalDateTime.now();
		this.elapsedTime = elapsedTime;
		this.errorMessage = null;
	}
}
```

`Log.class`에 대한 설명입니다.
해당 엔티티는 총 4개의 필드로 구성되어 있습니다.

1. UUID: id
2. LocalDateTime: requestTime;
3. long: elapsedTime;
4. String(**Nullable**): errorMessage

생성자를 생성할 때 소요 시간만 매개변수로 넘겨주면 로그를 생성할 수 있습니다.

## 문제 유형

- Interceptor
- RestControllerAdvice
- Http Header
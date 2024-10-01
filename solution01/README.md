# 테스트하는데 외부 서비스가 있으면 어려워!

## 개요

김세이는 책상 앞에서 머리를 긁적이며 깊은 고민에 빠져 있었다. 그는 테스트 코드를 돌려보려 했지만, 뭔가 마음에 걸리는 게 있었다.

“이 테스트는 좀 힘드네…” 김세이가 작은 한숨을 내쉬며 혼잣말을 했다. 마침 옆자리에서 일하던 외부 개발자가 그의 목소리를 들었다.

“무슨 문제 있어?” 외부 개발자가 물었다.

김세이는 모니터를 가리키며 말했다. “여기, 이 테스트… 뭔가 잘 안 풀려. 쉽게 끝날 줄 알았는데, 상황이 복잡해지고 있어.”

외부 개발자가 고개를 끄덕이며 모니터를 바라봤다. “음, 그래 보이네. 왜 이렇게 힘든 거야?”

“여러 이유가 있는데…” 김세이는 머뭇거리며 말을 이었다. “테스트하는 과정에서 뭔가 외부적인 부분 때문에 신경 쓸 게 많아지고 있어. 그게 좀 골칫거리야.”

외부 개발자는 미소를 지으며 물었다. “그렇다면 어떻게 하면 좋을까?”

김세이는 한참 고민하던 끝에 고개를 끄덕였다. “음… 뭔가 다른 방식으로 해결할 방법을 찾아봐야 할 것 같아. 기존 방식으론 한계가 있네.”

“그럼 그 방법으로 다시 시도해 보는 게 좋겠네,” 외부 개발자가 말했다. 김세이는 깊은 생각에 빠지며 자리로 돌아갔다. “좋아, 이제 어떻게 할지 알겠어. 다시 시도해 봐야지!”

> Author: Chat-GPT

## 문제

> `com.seikim.spring.readonly` 파일은 오로직 읽기만하시고 수정하지 마세요!

`com.seikim.spring.solution` 패키지 안에 있는 파일만 수정해서 문제를 해결해보세요!

테스트는 `com.seikim.spring.SolutionTest`을 실행시켜 모두다 성공시키면 성공입니다!

### 문제 1번

```java

@Test
void s3ClientInfoTest() {
	String serviceName = s3Client.serviceName();

	log.info("Service Name: {}", serviceName);

	assertThat(serviceName).isEqualTo("AWS S3 Service");
}
```

해당 테스트 코드는 AWS S3 클라이언트의 서비스 이름을 확인하는 테스트입니다. 이 테스트의 목적은 S3Client 객체가 올바르게 초기화되고, 해당 객체가 제공하는 서비스 이름이 예상한 값과 일치하는지 검증하는
것입니다.

### 문제 2번

```java

@ParameterizedTest
@EnumSource
void s3ClientPutTest(FileFixture fixture) throws IOException {
	RequestBody requestBody = fixture.toRequestBody();
	PutObjectRequest putObjectRequest = fixture.toPutObjectRequest();

	assertThatCode(() -> s3Client.putObject(putObjectRequest, requestBody))
			.doesNotThrowAnyException();
}
```

해당 테스트 코드는 AWS S3 클라이언트의 파일 업로드 기능을 검증하는 테스트입니다.

> 반환값은 상관없이 예외만 던지지 않으면 됩니다!
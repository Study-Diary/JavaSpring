package com.seikim.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.seikim.spring.readonly.AccountTest;
import com.seikim.spring.readonly.AccountTransaction;
import com.seikim.spring.readonly.AccountTransactionType;
import com.seikim.spring.readonly.BalanceTestFixture;
import com.seikim.spring.readonly.DepositTestFixture;
import com.seikim.spring.readonly.DiverseTransactionTestFixture;
import com.seikim.spring.readonly.WithdrawTestFixture;
import com.seikim.spring.readonly.data.request.AccountUpdateRequest;
import com.seikim.spring.readonly.data.response.AccountDetailResponse;
import com.seikim.spring.readonly.domain.Account;
import com.seikim.spring.readonly.repository.AccountRepository;
import com.seikim.spring.readonly.service.AccountService;

@SpringBootTest
class SolutionTest {
	private static final long DEFAULT_AMOUNT = 1_000_000L;
	private static final Logger log = LoggerFactory.getLogger(SolutionTest.class);
	private final List<Long> amounts = Collections.synchronizedList(new ArrayList<>());
	@Autowired
	private AccountService accountService;
	@Autowired
	private AccountRepository accountRepository;
	private Account account;

	@BeforeEach
	void setUp() {
		account = accountRepository.save(new Account(1, DEFAULT_AMOUNT));
		amounts.clear();
	}

	@AfterEach
	void log() {
		log.info("Result: {}", amounts);
	}

	@DisplayName("동일한 유저에게 요청이 여러개가 들어오면 차례대로 실행됩니다.")
	@ParameterizedTest
	@EnumSource(BalanceTestFixture.class)
	void balanceTest(BalanceTestFixture fixture) throws Exception {
		/* Given */
		ExecutorService executorService = Executors.newFixedThreadPool(fixture.getThreadSize());

		/* When */
		process(fixture, executorService);

		/* Then */
		Assertions.assertThat(amounts).containsExactlyElementsOf(fixture.getResult(DEFAULT_AMOUNT));
	}

	@DisplayName("동일한 유저에게 잔고 입금 요청이 동시에 2개 이상 발생하는 경우 한 요청만 성공하고, 나머지는 실패합니다")
	@ParameterizedTest
	@EnumSource(DepositTestFixture.class)
	void depositTest(DepositTestFixture fixture) throws Exception {
		/* Given */
		ExecutorService executorService = Executors.newFixedThreadPool(fixture.getThreadSize());

		/* When */
		process(fixture, executorService);

		/* Then */
		Assertions.assertThat(amounts).containsExactlyElementsOf(fixture.getResult(DEFAULT_AMOUNT));
	}

	@DisplayName("동일한 유저에게 잔고 입금과 출금 요청이 동시에 발생할 수 있습니다. 이 경우 요청은 차례대로 실행됩니다.")
	@ParameterizedTest
	@EnumSource(DiverseTransactionTestFixture.class)
	void diverseTransactionTest(DiverseTransactionTestFixture fixture) throws Exception {
		/* Given */
		ExecutorService executorService = Executors.newFixedThreadPool(fixture.getThreadSize());

		/* When */
		process(fixture, executorService);

		/* Then */
		Assertions.assertThat(amounts).containsExactlyElementsOf(fixture.getResult(DEFAULT_AMOUNT));
	}

	@DisplayName("동일한 유저에게 잔고 출금 요청이 동시에 2개 이상 발생하는 경우, 요청이 차례대로 실행됩니다.")
	@ParameterizedTest
	@EnumSource(WithdrawTestFixture.class)
	void withdrawTest(WithdrawTestFixture fixture) throws InterruptedException {
		/* Given */
		ExecutorService executorService = Executors.newFixedThreadPool(fixture.getThreadSize());

		/* When */
		process(fixture, executorService);

		/* Then */
		Assertions.assertThat(amounts).containsExactlyElementsOf(fixture.getResult(DEFAULT_AMOUNT));
	}

	private void process(AccountTest test, ExecutorService executorService) throws InterruptedException {
		for (AccountTransaction transaction : test.getTransactions()) {
			if (transaction.type().equals(AccountTransactionType.BALANCE)) {
				executorService.execute(() -> {
					AccountDetailResponse response = accountService.balance(account.getId());
					amounts.add(response.amount());
				});
			}
			if (transaction.type().equals(AccountTransactionType.DEPOSIT)) {
				executorService.execute(() -> {
					AccountDetailResponse response = accountService.deposit(account.getId(),
							new AccountUpdateRequest(transaction.amount()));
					amounts.add(response.amount());
				});
			}
			if (transaction.type().equals(AccountTransactionType.WITHDRAW)) {
				executorService.execute(() -> {
					AccountDetailResponse response = accountService.withdraw(account.getId(),
							new AccountUpdateRequest(transaction.amount()));
					amounts.add(response.amount());
				});
			}
			Thread.sleep(10);
		}
		executorService.shutdown();
		try {
			if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
				executorService.shutdownNow();
			}
		} catch (InterruptedException e) {
			executorService.shutdownNow();
			Thread.currentThread().interrupt();
		}
	}
}

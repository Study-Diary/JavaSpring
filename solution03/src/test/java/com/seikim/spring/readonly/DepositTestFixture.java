package com.seikim.spring.readonly;

import java.util.List;

public enum DepositTestFixture implements AccountTest {
	ONE(new AccountTransaction[] {
			AccountTransaction.deposit(100_000L),
			AccountTransaction.deposit(200_000L),
	}),
	TWO(new AccountTransaction[] {
			AccountTransaction.deposit(100_000_000L),
			AccountTransaction.deposit(1_000_000_000L),
	});

	private final AccountTransaction[] transactions;

	DepositTestFixture(AccountTransaction[] transactions) {
		this.transactions = transactions;
	}

	@Override
	public AccountTransaction[] getTransactions() {
		return transactions;
	}

	@Override
	public int getThreadSize() {
		return transactions.length;
	}

	@Override
	public List<Long> getResult(long amount) {
		return List.of(amount + transactions[0].amount());
	}
}

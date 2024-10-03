package com.seikim.spring.readonly;

import java.util.List;

public enum DepositTestFixture implements AccountTest {
	ONE(new AccountTransaction[] {
			new AccountTransaction(100_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(200_000L, AccountTransactionType.DEPOSIT),
	}),
	TWO(new AccountTransaction[] {
			new AccountTransaction(100_000_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(1_000_000_000L, AccountTransactionType.DEPOSIT),
	}),
	;

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

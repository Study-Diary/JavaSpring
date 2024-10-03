package com.seikim.spring.readonly;

import java.util.ArrayList;
import java.util.List;

public enum BalanceTestFixture implements AccountTest {
	ONE(new AccountTransaction[] {
			new AccountTransaction(100_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
			new AccountTransaction(200_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
	}),
	TWO(new AccountTransaction[] {
			new AccountTransaction(100_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
			new AccountTransaction(200_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(200_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
			new AccountTransaction(200_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
	}),
	;

	private final AccountTransaction[] transactions;

	BalanceTestFixture(AccountTransaction[] transactions) {
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
		List<Long> result = new ArrayList<>();
		for (AccountTransaction transaction : transactions) {
			switch (transaction.type()) {
				case DEPOSIT -> amount += transaction.amount();
				case WITHDRAW -> amount -= transaction.amount();
			}
			result.add(amount);
		}
		return result;
	}
}

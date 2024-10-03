package com.seikim.spring.readonly;

import java.util.ArrayList;
import java.util.List;

public enum DiverseTransactionTestFixture implements AccountTest {
	ONE(new AccountTransaction[] {
			new AccountTransaction(1_000_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(500_000L, AccountTransactionType.WITHDRAW),
	}),
	TWO(new AccountTransaction[] {
			new AccountTransaction(500_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(1_000_000L, AccountTransactionType.WITHDRAW),
	}),
	THREE(new AccountTransaction[] {
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
			new AccountTransaction(500_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(1_000_000L, AccountTransactionType.WITHDRAW),
	}),
	FOUR(new AccountTransaction[] {
			new AccountTransaction(500_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(1_000_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
	}),
	FIVE(new AccountTransaction[] {
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
			new AccountTransaction(500_000L, AccountTransactionType.DEPOSIT),
			new AccountTransaction(1_000_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
	}),
	;

	private final AccountTransaction[] transactions;

	DiverseTransactionTestFixture(AccountTransaction[] transactions) {
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

package com.seikim.spring.readonly;

import java.util.ArrayList;
import java.util.List;

public enum DiverseTransactionTestFixture implements AccountTest {
	ONE(new AccountTransaction[] {
			AccountTransaction.deposit(1_000_000L),
			AccountTransaction.withdraw(500_000L)
	}),
	TWO(new AccountTransaction[] {
			AccountTransaction.deposit(500_000L),
			AccountTransaction.withdraw(1_000_000L)
	}),
	THREE(new AccountTransaction[] {
			AccountTransaction.balance(),
			AccountTransaction.deposit(500_000L),
			AccountTransaction.withdraw(1_000_000L)
	}),
	FOUR(new AccountTransaction[] {
			AccountTransaction.deposit(500_000L),
			AccountTransaction.withdraw(1_000_000L),
			AccountTransaction.balance()
	}),
	FIVE(new AccountTransaction[] {
			AccountTransaction.balance(),
			AccountTransaction.deposit(500_000L),
			AccountTransaction.withdraw(1_000_000L),
			AccountTransaction.balance()
	});

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

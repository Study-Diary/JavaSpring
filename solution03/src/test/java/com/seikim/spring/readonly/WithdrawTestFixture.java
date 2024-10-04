package com.seikim.spring.readonly;

import java.util.ArrayList;
import java.util.List;

public enum WithdrawTestFixture implements AccountTest {
	ONE(new AccountTransaction[] {
			AccountTransaction.withdraw(100_000L),
			AccountTransaction.withdraw(200_000L),
			AccountTransaction.withdraw(300_000L),
			AccountTransaction.withdraw(400_000L)
	}),
	TWO(new AccountTransaction[] {
			AccountTransaction.withdraw(200_000L),
			AccountTransaction.withdraw(100_000L)
	}),
	THREE(new AccountTransaction[] {
			AccountTransaction.withdraw(200_000L),
			AccountTransaction.withdraw(100_000L),
			AccountTransaction.balance(),
			AccountTransaction.withdraw(100_000L),
			AccountTransaction.withdraw(100_000L)
	});

	private final AccountTransaction[] transactions;

	WithdrawTestFixture(AccountTransaction[] transactions) {
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

package com.seikim.spring.readonly;

import java.util.ArrayList;
import java.util.List;

public enum WithdrawTestFixture implements AccountTest {
	ONE(new AccountTransaction[] {
			new AccountTransaction(100_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(200_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(300_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(400_000L, AccountTransactionType.WITHDRAW),
	}),
	TWO(new AccountTransaction[] {
			new AccountTransaction(200_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(100_000L, AccountTransactionType.WITHDRAW),
	}),
	THREE(new AccountTransaction[] {
			new AccountTransaction(200_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(100_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(0L, AccountTransactionType.BALANCE),
			new AccountTransaction(100_000L, AccountTransactionType.WITHDRAW),
			new AccountTransaction(100_000L, AccountTransactionType.WITHDRAW),
	}),
	;

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

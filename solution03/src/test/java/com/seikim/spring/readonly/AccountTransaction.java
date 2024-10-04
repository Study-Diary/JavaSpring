package com.seikim.spring.readonly;

public record AccountTransaction(long amount, AccountTransactionType type) {
	public static AccountTransaction balance() {
		return new AccountTransaction(0L, AccountTransactionType.BALANCE);
	}

	public static AccountTransaction withdraw(long amount) {
		return new AccountTransaction(amount, AccountTransactionType.WITHDRAW);
	}

	public static AccountTransaction deposit(long amount) {
		return new AccountTransaction(amount, AccountTransactionType.DEPOSIT);
	}
}

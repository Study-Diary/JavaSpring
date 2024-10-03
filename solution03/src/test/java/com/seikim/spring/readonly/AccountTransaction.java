package com.seikim.spring.readonly;

public record AccountTransaction(long amount, AccountTransactionType type) {
}

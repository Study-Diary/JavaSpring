package com.seikim.spring.readonly.data.response;

import com.seikim.spring.readonly.domain.Account;

public record AccountDetailResponse(
		int accountId,
		long amount
) {

	public static AccountDetailResponse from(Account account) {
		return new AccountDetailResponse(account.getId(), account.getAmount());
	}
}

package com.seikim.spring.readonly.service;

import com.seikim.spring.readonly.data.request.AccountUpdateRequest;
import com.seikim.spring.readonly.data.response.AccountDetailResponse;

public interface AccountService {
	AccountDetailResponse balance(int accountId);

	AccountDetailResponse deposit(final int accountId, final AccountUpdateRequest request);

	AccountDetailResponse withdraw(final int accountId, final AccountUpdateRequest request);
}

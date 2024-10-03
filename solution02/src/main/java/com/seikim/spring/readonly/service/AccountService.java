package com.seikim.spring.readonly.service;

import org.springframework.stereotype.Service;

import com.seikim.spring.readonly.data.request.AccoutUpdateRequest;
import com.seikim.spring.readonly.data.response.AccountDetailResponse;
import com.seikim.spring.readonly.domain.Account;
import com.seikim.spring.readonly.repository.AccountRepository;
import com.seikim.spring.readonly.utils.AccountServiceUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {
	private final AccountRepository accountRepository;

	public AccountDetailResponse deposit(final AccoutUpdateRequest request) {
		Account findAccount = AccountServiceUtils.findById(accountRepository, request.accountId());

		findAccount.deposit(request.amount());
		Account updatedAccount = accountRepository.save(findAccount);

		return AccountDetailResponse.from(updatedAccount);
	}

	public AccountDetailResponse withdraw(AccoutUpdateRequest request) {
		Account findAccount = AccountServiceUtils.findById(accountRepository, request.accountId());

		findAccount.withdraw(request.amount());
		Account updatedAccount = accountRepository.save(findAccount);

		return AccountDetailResponse.from(updatedAccount);
	}
}

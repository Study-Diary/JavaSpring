package com.seikim.spring.solution;

import org.springframework.stereotype.Service;

import com.seikim.spring.readonly.data.request.AccountUpdateRequest;
import com.seikim.spring.readonly.data.response.AccountDetailResponse;
import com.seikim.spring.readonly.domain.Account;
import com.seikim.spring.readonly.repository.AccountRepository;
import com.seikim.spring.readonly.service.AccountService;
import com.seikim.spring.readonly.utils.AccountServiceUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
	private final AccountRepository accountRepository;

	public AccountDetailResponse balance(int accountId) {
		Account findAccount = AccountServiceUtils.findById(accountRepository, accountId);
		return AccountDetailResponse.from(findAccount);
	}

	public AccountDetailResponse deposit(final int accountId, final AccountUpdateRequest request) {
		Account findAccount = AccountServiceUtils.findById(accountRepository, accountId);
		findAccount.deposit(request.amount());
		Account updatedAccount = accountRepository.save(findAccount);
		return AccountDetailResponse.from(updatedAccount);
	}

	public AccountDetailResponse withdraw(final int accountId, final AccountUpdateRequest request) {
		Account findAccount = AccountServiceUtils.findById(accountRepository, accountId);
		findAccount.withdraw(request.amount());
		Account updatedAccount = accountRepository.save(findAccount);
		return AccountDetailResponse.from(updatedAccount);
	}
}

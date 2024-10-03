package com.seikim.spring.readonly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seikim.spring.readonly.data.request.AccountUpdateRequest;
import com.seikim.spring.readonly.data.response.AccountDetailResponse;
import com.seikim.spring.solution.AccountServiceImpl;

import lombok.RequiredArgsConstructor;

@RequestMapping("/account")
@RequiredArgsConstructor
@RestController
public class AccountController {

	private final AccountServiceImpl accountService;

	@GetMapping("/{accountId}/balance")
	public ResponseEntity<AccountDetailResponse> balance(@PathVariable final int accountId) {
		AccountDetailResponse response = accountService.balance(accountId);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{accountId}/deposit")
	public ResponseEntity<AccountDetailResponse> deposit(
			@PathVariable final int accountId,
			@RequestBody final AccountUpdateRequest request
	) {
		AccountDetailResponse response = accountService.deposit(accountId, request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{accountId}/withdraw")
	public ResponseEntity<AccountDetailResponse> withdraw(
			@PathVariable final int accountId,
			@RequestBody final AccountUpdateRequest request) {
		AccountDetailResponse response = accountService.withdraw(accountId, request);
		return ResponseEntity.ok(response);
	}
}

package com.seikim.spring.readonly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seikim.spring.readonly.data.request.AccoutUpdateRequest;
import com.seikim.spring.readonly.data.response.AccountDetailResponse;
import com.seikim.spring.readonly.service.AccountService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/account")
@RequiredArgsConstructor
@RestController
public class AccountController {

	private final AccountService accountService;

	@PutMapping("/deposit")
	public ResponseEntity<AccountDetailResponse> deposit(@RequestBody final AccoutUpdateRequest request) {
		AccountDetailResponse response = accountService.deposit(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/withdraw")
	public ResponseEntity<AccountDetailResponse> withdraw(@RequestBody final AccoutUpdateRequest request) {
		AccountDetailResponse response = accountService.withdraw(request);
		return ResponseEntity.ok(response);
	}
}

package com.seikim.spring.readonly.service;

import org.springframework.stereotype.Service;

import com.seikim.spring.readonly.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AccountService {
	private final AccountRepository accountRepository;


}

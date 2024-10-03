package com.seikim.spring.readonly.utils;

import java.util.NoSuchElementException;

import com.seikim.spring.readonly.domain.Account;
import com.seikim.spring.readonly.repository.AccountRepository;

public class AccountServiceUtils {
	private AccountServiceUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static Account findById(AccountRepository repository, int id) {
		return repository.findById(id).orElseThrow(() -> new NoSuchElementException(id + " Not Found"));
	}
}

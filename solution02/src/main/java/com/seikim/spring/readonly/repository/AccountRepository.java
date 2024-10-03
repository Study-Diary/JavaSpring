package com.seikim.spring.readonly.repository;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seikim.spring.readonly.common.BaseRepository;
import com.seikim.spring.readonly.common.id.IntegerIdentifierGenerate;
import com.seikim.spring.readonly.common.strategy.GenerateIdentifierStrategy;
import com.seikim.spring.readonly.domain.Account;

@Repository
public class AccountRepository extends BaseRepository<Account, Integer> {

	@Autowired
	public AccountRepository(Map<Class<?>, GenerateIdentifierStrategy<?>> generateIdentifierStrategies) {
		super(generateIdentifierStrategies, new IntegerIdentifierGenerate());
	}
}

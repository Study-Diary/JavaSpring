package com.seikim.spring.readonly.repository;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.seikim.spring.readonly.common.BaseRepository;
import com.seikim.spring.readonly.common.id.UUIDIdentifierGenerate;
import com.seikim.spring.readonly.common.strategy.GenerateIdentifierStrategy;
import com.seikim.spring.readonly.domain.Log;

@Repository
public class LogRepository extends BaseRepository<Log, UUID> {

	@Autowired
	public LogRepository(Map<Class<?>, GenerateIdentifierStrategy<?>> generateIdentifierStrategies) {
		super(generateIdentifierStrategies, new UUIDIdentifierGenerate());
	}

	public void clear() {
		entityMap.clear();
	}
}

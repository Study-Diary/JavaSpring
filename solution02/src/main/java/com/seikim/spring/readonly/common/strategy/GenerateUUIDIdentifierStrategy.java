package com.seikim.spring.readonly.common.strategy;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.seikim.spring.readonly.common.Entity;

@Component
public class GenerateUUIDIdentifierStrategy implements GenerateIdentifierStrategy<UUID> {
	@Override
	public Class<UUID> getIdType() {
		return UUID.class;
	}

	@Override
	public Entity<UUID> generate(UUID id, Entity<?> e) {
		if (e.getIdentifierClassType().equals(getIdType())) {
			@SuppressWarnings("unchecked")
			Entity<UUID> entity = (Entity<UUID>)e;
			entity.init(id);
			return entity;
		}
		throw new IllegalArgumentException();
	}
}

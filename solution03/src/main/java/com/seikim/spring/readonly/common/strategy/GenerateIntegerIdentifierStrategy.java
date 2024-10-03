package com.seikim.spring.readonly.common.strategy;

import org.springframework.stereotype.Component;

import com.seikim.spring.readonly.common.Entity;

@Component
public class GenerateIntegerIdentifierStrategy implements GenerateIdentifierStrategy<Integer> {
	@Override
	public Class<Integer> getIdType() {
		return Integer.class;
	}

	@Override
	public Entity<Integer> generate(Integer id, Entity<?> e) {
		if (e.getIdentifierClassType().equals(getIdType())) {
			@SuppressWarnings("unchecked")
			Entity<Integer> entity = (Entity<Integer>)e;
			entity.initIdentifier(id);
			return entity;
		}
		throw new IllegalArgumentException();
	}
}

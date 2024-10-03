package com.seikim.spring.readonly.common.strategy;

import org.springframework.stereotype.Component;

import com.seikim.spring.readonly.common.Entity;
import com.seikim.spring.readonly.common.id.IdentifierGenerate;

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
			entity.init(id);
			return entity;
		}
		throw new IllegalArgumentException();
	}
}

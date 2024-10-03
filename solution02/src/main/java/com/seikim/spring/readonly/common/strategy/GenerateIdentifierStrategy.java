package com.seikim.spring.readonly.common.strategy;

import com.seikim.spring.readonly.common.Entity;

public interface GenerateIdentifierStrategy<T> {
	Class<T> getIdType();

	Entity<T> generate(T id, Entity<?> e);
}

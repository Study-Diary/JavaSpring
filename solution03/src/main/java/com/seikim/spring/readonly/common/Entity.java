package com.seikim.spring.readonly.common;

public interface Entity<T> {
	Class<T> getIdentifierClassType();

	T getId();

	void initIdentifier(T id);
}

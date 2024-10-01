package com.seikim.spring.readonly.common;

public interface Entity<I> {
	I getId();

	void init(I id);
}

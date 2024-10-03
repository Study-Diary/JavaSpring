package com.seikim.spring.readonly.common;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public abstract class IntegerEntity implements Entity<Integer> {
	private Integer id;

	@Override
	public Class<Integer> getIdentifierClassType() {
		return Integer.class;
	}

	@Override
	public void init(final Integer id) {
		if (id == null) {
			throw new IllegalArgumentException("식별자는 NULL 값을 가질 수 없습니다.");
		}
		this.id = id;
	}
}

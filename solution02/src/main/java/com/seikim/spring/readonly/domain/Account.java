package com.seikim.spring.readonly.domain;

import com.seikim.spring.readonly.common.IntegerEntity;

import lombok.Getter;
import lombok.ToString;

/**
 * {@link  com.seikim.spring.readonly.domain.Member}와 {@link com.seikim.spring.readonly.domain.Account}는 `OneToOne`관계입니다.
 */
@ToString(callSuper = true)
@Getter
public class Account extends IntegerEntity {
	private long amount;

	public Account(int id, long amount) {
		super();
		init(id);
		this.amount = amount;
	}
}

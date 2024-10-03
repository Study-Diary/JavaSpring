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
		initIdentifier(id);
		this.amount = amount;
	}

	public void deposit(long amount) {
		this.amount += amount;
	}

	public void withdraw(long amount) {
		if (this.amount < amount) {
			throw new IllegalArgumentException("계좌 잔액이 부족합니다.");
		}
		this.amount -= amount;
	}
}

package com.seikim.spring.readonly.common.id;

import java.util.concurrent.atomic.AtomicLong;

public class LongIdentifierGenerate implements IdentifierGenerate<Long> {
	private final AtomicLong atomicLong = new AtomicLong(1);

	@Override
	public Long generate() {
		return atomicLong.getAndIncrement();
	}
}

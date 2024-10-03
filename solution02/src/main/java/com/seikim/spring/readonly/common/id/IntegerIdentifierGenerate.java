package com.seikim.spring.readonly.common.id;

import java.util.concurrent.atomic.AtomicInteger;

public class IntegerIdentifierGenerate implements IdentifierGenerate<Integer> {
	private final AtomicInteger atomicInteger = new AtomicInteger(1);

	@Override
	public Integer generate() {
		return atomicInteger.getAndIncrement();
	}
}

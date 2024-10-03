package com.seikim.spring.readonly.common.id;

import java.util.UUID;

public class UUIDIdentifierGenerate implements IdentifierGenerate<UUID> {
	public UUID generate() {
		return UUID.randomUUID();
	}
}

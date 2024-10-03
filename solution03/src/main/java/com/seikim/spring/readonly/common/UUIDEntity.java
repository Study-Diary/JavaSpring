package com.seikim.spring.readonly.common;

import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public abstract class UUIDEntity implements Entity<UUID> {
	private UUID id;

	public UUIDEntity(UUID id) {
		this.id = id;
	}

	@Override
	public Class<UUID> getIdentifierClassType() {
		return UUID.class;
	}

	@Override
	public void initIdentifier(final UUID id) {
		if (this.id == null) {
			this.id = (id != null) ? id : UUID.randomUUID();
		}
	}
}

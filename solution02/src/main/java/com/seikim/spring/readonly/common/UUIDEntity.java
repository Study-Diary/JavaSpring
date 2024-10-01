package com.seikim.spring.readonly.common;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public abstract class UUIDEntity implements Entity<UUID> {
	private UUID id;

	@Override
	public void init(final UUID id) {
		if (this.id == null) {
			this.id = (id != null) ? id : UUID.randomUUID();
		}
	}
}

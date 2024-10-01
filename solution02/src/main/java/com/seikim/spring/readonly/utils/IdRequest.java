package com.seikim.spring.readonly.utils;

public record IdRequest<T>(
		String id,
		T body
) {
}

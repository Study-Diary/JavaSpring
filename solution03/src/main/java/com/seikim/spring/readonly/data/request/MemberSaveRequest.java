package com.seikim.spring.readonly.data.request;

public record MemberSaveRequest(
		int age,
		String name,
		long amount
) {
}

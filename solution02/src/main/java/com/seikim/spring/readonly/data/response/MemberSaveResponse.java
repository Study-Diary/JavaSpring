package com.seikim.spring.readonly.data.response;

public record MemberSaveResponse(
		int memberId,
		long accountAmount
) {
}

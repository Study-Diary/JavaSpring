package com.seikim.spring.readonly.data.request;

public record AccoutUpdateRequest(
		int accountId,
		long amount
) {
}

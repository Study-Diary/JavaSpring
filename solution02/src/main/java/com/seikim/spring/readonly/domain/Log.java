package com.seikim.spring.readonly.domain;

import java.time.LocalDateTime;

import com.seikim.spring.readonly.common.UUIDEntity;

import lombok.Getter;
import lombok.ToString;

@ToString(callSuper = true)
@Getter
public class Log extends UUIDEntity {

	private final LocalDateTime requestTime;    // API 요청 시간
	private final long elapsedTime;    // API 경과 시간
	private final String errorMessage;

	public Log(long elapsedTime) {
		super(null);
		this.requestTime = LocalDateTime.now();
		this.elapsedTime = elapsedTime;
		this.errorMessage = null;
	}

	public Log(long elapsedTime, Exception exception) {
		super(null);
		this.requestTime = LocalDateTime.now();
		this.elapsedTime = elapsedTime;
		if (exception != null) {
			this.errorMessage = exception.getMessage();
			return;
		}
		this.errorMessage = null;
	}
}

package com.seikim.spring.readonly.data.response;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.seikim.spring.readonly.domain.Log;

public record LogDetailResponse(
		List<LogDetail> logs,
		int count
) {

	public static LogDetailResponse from(List<Log> logs) {
		return new LogDetailResponse(
				logs.stream().map(LogDetail::from).toList(),
				logs.size()
		);
	}

	public record LogDetail(
			UUID logId,
			String requestTime,
			long elapsedTime,
			@JsonInclude(JsonInclude.Include.NON_NULL)
			String errorMessage
	) {
		public static LogDetail from(Log log) {
			return new LogDetail(log.getId(),
					log.getRequestTime().toString(),
					log.getElapsedTime(),
					log.getErrorMessage()
			);
		}
	}
}

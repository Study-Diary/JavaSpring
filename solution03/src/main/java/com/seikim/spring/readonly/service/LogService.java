package com.seikim.spring.readonly.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.seikim.spring.readonly.data.response.LogDetailResponse;
import com.seikim.spring.readonly.domain.Log;
import com.seikim.spring.readonly.repository.LogRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LogService {
	private final LogRepository logRepository;

	public LogDetailResponse findAll() {
		List<Log> findLogs = logRepository.findAll();
		return LogDetailResponse.from(findLogs);
	}
}

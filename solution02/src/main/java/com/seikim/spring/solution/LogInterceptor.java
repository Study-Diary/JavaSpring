package com.seikim.spring.solution;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;

import com.seikim.spring.readonly.repository.LogRepository;

import lombok.RequiredArgsConstructor;

/**
 * 시간을 계산할때는 {@link StopWatch}를 사용해주세요!
 */
@RequiredArgsConstructor
@Component
public class LogInterceptor implements HandlerInterceptor {
	private final LogRepository logRepository;
}

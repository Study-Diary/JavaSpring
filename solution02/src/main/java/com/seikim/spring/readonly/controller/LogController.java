package com.seikim.spring.readonly.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seikim.spring.readonly.data.response.LogDetailResponse;
import com.seikim.spring.readonly.service.LogService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/log")
@RestController
public class LogController {

	private final LogService logService;

	@GetMapping
	public ResponseEntity<LogDetailResponse> findAll() {
		return ResponseEntity.ok(logService.findAll());
	}
}

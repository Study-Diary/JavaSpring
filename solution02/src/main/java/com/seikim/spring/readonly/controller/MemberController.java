package com.seikim.spring.readonly.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seikim.spring.readonly.data.request.MemberSaveRequest;
import com.seikim.spring.readonly.data.response.MemberSaveResponse;
import com.seikim.spring.readonly.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/member")
@RestController
@Slf4j
public class MemberController {

	private final MemberService memberService;

	@PostMapping
	public ResponseEntity<MemberSaveResponse> save(
			@RequestBody final MemberSaveRequest request
	) {
		MemberSaveResponse savedMember = memberService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedMember);
	}
}

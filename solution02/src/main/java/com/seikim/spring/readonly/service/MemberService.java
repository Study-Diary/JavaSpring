package com.seikim.spring.readonly.service;

import org.springframework.stereotype.Service;

import com.seikim.spring.readonly.data.request.MemberSaveRequest;
import com.seikim.spring.readonly.data.response.MemberSaveResponse;
import com.seikim.spring.readonly.domain.Account;
import com.seikim.spring.readonly.domain.Member;
import com.seikim.spring.readonly.repository.AccountRepository;
import com.seikim.spring.readonly.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final AccountRepository accountRepository;

	public MemberSaveResponse save(final MemberSaveRequest request) {
		Member member = new Member(request.age(), request.name());
		Member savedMember = memberRepository.save(member);

		log.info("Saved account with id: {}, name: {}", savedMember.getId(), savedMember.getName());

		Account account = new Account(savedMember.getId(), request.amount());
		Account savedAccount = accountRepository.save(account);

		log.info("Saved account with id: {}, amount: {}", savedAccount.getId(), savedAccount.getAmount());

		return new MemberSaveResponse(savedMember.getId(), savedAccount.getAmount());
	}
}

package com.seikim.spring.readonly;

import com.seikim.spring.readonly.data.request.MemberSaveRequest;
import com.seikim.spring.readonly.data.response.MemberSaveResponse;
import com.seikim.spring.readonly.domain.Member;

public enum MemberFixture {
	KIM(1, 25, "김세이", 1_000_000L);

	private int id;
	private int age;
	private String name;
	private long amount;

	MemberFixture(int id, int age, String name, long amount) {
		this.id = id;
		this.age = age;
		this.name = name;
		this.amount = amount;
	}

	public Member toMember() {
		Member member = new Member(age, name);
		member.initIdentifier(id);
		return member;
	}

	public MemberSaveResponse toSaveResponse() {
		return new MemberSaveResponse(id, amount);
	}

	public MemberSaveRequest toSaveRequest() {
		return new MemberSaveRequest(age, name, amount);
	}

	public int getAge() {
		return age;
	}

	public long getAmount() {
		return amount;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

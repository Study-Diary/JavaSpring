package com.seikim.spring.readonly.repository;

import org.springframework.stereotype.Repository;

import com.seikim.spring.readonly.common.BaseRepository;
import com.seikim.spring.readonly.domain.Member;

@Repository
public class MemberRepository extends BaseRepository<Member, Integer> {
}

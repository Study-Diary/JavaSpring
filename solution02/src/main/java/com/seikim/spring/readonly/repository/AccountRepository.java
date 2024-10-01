package com.seikim.spring.readonly.repository;

import org.springframework.stereotype.Repository;

import com.seikim.spring.readonly.common.BaseRepository;
import com.seikim.spring.readonly.domain.Account;

@Repository
public class AccountRepository extends BaseRepository<Account, Integer> {
}

package com.seikim.spring.readonly.repository;

import org.springframework.stereotype.Repository;

import com.seikim.spring.readonly.common.BaseRepository;
import com.seikim.spring.readonly.domain.Log;

@Repository
public class LogRepository extends BaseRepository<Log, String> {

	public void clear() {
		entityMap.clear();
	}
}

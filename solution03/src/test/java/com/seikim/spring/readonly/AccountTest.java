package com.seikim.spring.readonly;

import java.util.List;

public interface AccountTest {
	int getThreadSize();

	List<Long> getResult(long amount);

	AccountTransaction[] getTransactions();
}

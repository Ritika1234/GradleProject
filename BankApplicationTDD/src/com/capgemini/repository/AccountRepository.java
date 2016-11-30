package com.capgemini.repository;

import com.capgmeini.model.Account;

public interface AccountRepository {
	
	boolean save(Account account);
	Account searchAccount(int accountNumber);

}

package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.exceptions.InvalidAmountException;
import com.capgmeini.model.Account;

public interface AccountService {

	Account createAccount(int accountNumber, int amount) throws InsufficientInitialBalanceException;
	int depositAmount(int accountNumber,int amount) throws InvalidAmountException;
	int withdrawAmount(int accountNumber,int amount) throws InsufficientBalanceException,InvalidAmountException;
	boolean transferAmount(int accountNumber1,int accountNumber2,int amount) throws InsufficientBalanceException,InvalidAmountException,InvalidAccountNumberException;
}
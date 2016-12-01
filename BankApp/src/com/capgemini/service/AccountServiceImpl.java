package com.capgemini.service;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.exceptions.InvalidAmountException;
import com.capgemini.repository.AccountRepository;
import com.capgmeini.model.Account;

public class AccountServiceImpl implements AccountService {
	AccountRepository accountRepository;
	public AccountServiceImpl(AccountRepository accountRepository)
	{
		this.accountRepository=accountRepository;
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialBalanceException
	{
		
		if(amount<500)
		{
			throw new InsufficientInitialBalanceException();
		}
		Account account = new Account();
		
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
	}

	@Override
	public int depositAmount(int accountNumber, int amount) throws InvalidAmountException{

	
		
		if(amount <=0)
		{
			throw new InvalidAmountException();
		}
		
		Account account = null;
		
		if(amount>0)
		{
			account= accountRepository.searchAccount(101);
			amount=account.getAmount()+amount;
			account.setAmount(amount);
		}
		return account.getAmount();
	
	}

	@Override
	public int withdrawAmount(int accountNumber, int amount)
			throws InsufficientBalanceException, InvalidAmountException 
	{
	Account account=accountRepository.searchAccount(101);
	
	if(amount<=0)
	{
		throw new InvalidAmountException();
	}
	
	
	else if(account.getAmount()-amount<=500)
	{
		throw new InsufficientBalanceException();
	}
	
	if(amount>0 && amount >= (amount-500))
	{
		
		amount=account.getAmount()-amount;
		account.setAmount(amount);
	}
	
		
		return account.getAmount();
	}

	@Override
	public boolean transferAmount(int accountNumber1, int accountNumber2,
			int amount) throws InsufficientBalanceException, InvalidAmountException, InvalidAccountNumberException {
		if((Integer.toString(accountNumber1).length()) < 3 || (Integer.toString(accountNumber2).length()) < 3 )
		{
			throw new InvalidAccountNumberException();
		}
		
		Account account = accountRepository.searchAccount(101);
		
		if(amount<=0)
		{
			throw new InvalidAmountException();
		}
		
		if(amount >0 && (account.getAmount()-amount<=500))
		{
			throw new InsufficientBalanceException();
		}
		
		return true;
		
		
}

	
}

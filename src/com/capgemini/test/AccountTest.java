package com.capgemini.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialBalanceException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.exceptions.InvalidAmountException;
import com.capgemini.repository.AccountRepository;
import com.capgemini.service.AccountService;
import com.capgemini.service.AccountServiceImpl;
import com.capgmeini.model.Account;

import static org.mockito.Mockito.when;
public class AccountTest {

	@Mock
	AccountRepository accountRepository;
	
	AccountService accountService;
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		accountService = new AccountServiceImpl(accountRepository);
	}
	
	/* create account
	 * 1. when the amount is less than 500, system should throw exception
	 * 2. when the valid info is passed account should be created successfully.
	 */
	
	@Test(expected=com.capgemini.exceptions.InsufficientInitialBalanceException.class)
	public void whenTheAmountIsLessThan500SystemShouldThrowException() throws InsufficientInitialBalanceException
	{
		accountService.createAccount(101, 400);
	}
	
	@Test
	//@Ignore
	public void whenTheValidInfoIsPassedAccountShouldBeCreatedSuccessfully() throws InsufficientInitialBalanceException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(5000);
		
		when(accountRepository.save(account)).thenReturn(true);
		assertEquals(account,accountService.createAccount(101, 5000));
	}

	@Test(expected = com.capgemini.exceptions.InvalidAmountException.class)
	public void whenDepositAmountIsInvalid() throws InvalidAmountException
	{
		accountService.depositAmount(101, -211);
	}
	
	@Test
	public void whenDepositAmountIsValidAmountShouldBeDeposited() throws InvalidAmountException 
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(1000);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		
		//when(accountRepository.save(account)).thenReturn(true);
		assertEquals(3000, accountService.depositAmount(101, 2000));
	}

	@Test(expected=com.capgemini.exceptions.InvalidAmountException.class)
	public void whenWithDrawAmountIsInvalid() throws InvalidAmountException, InsufficientBalanceException
	{
		accountService.withdrawAmount(101, -111);
	}
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenWithDrawAmountIsValidButLessThanCurrentBalance() throws InsufficientBalanceException, InvalidAmountException
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(600);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		accountService.withdrawAmount(101, 1500);
	}
	
	@Test
	public void whenWithdrawAmountIsValid() throws InsufficientBalanceException, InvalidAmountException
	{
		Account account= new Account();
		account.setAccountNumber(101);
		account.setAmount(6000);
		
		
		when(accountRepository.searchAccount(101)).thenReturn(account);
		
		accountService.withdrawAmount(100, 200);
	}
	@Test(expected= com.capgemini.exceptions.InvalidAmountException.class)
	public void whenTransferAmountIsInvalid() throws InsufficientBalanceException, InvalidAmountException, InvalidAccountNumberException 
	{
		accountService.transferAmount(101, 102, -200);
	}
	
	@Test(expected=com.capgemini.exceptions.InsufficientBalanceException.class)
	public void whenTransferAmountIsLessThanCurrentBalance() throws InsufficientBalanceException, InvalidAmountException, InvalidAccountNumberException 
	{
		Account account = new Account();
		account.setAccountNumber(101);
		account.setAmount(600);
		when(accountRepository.searchAccount(101)).thenReturn(account);
		accountService.transferAmount(101, 102, 500);
	}
	
	@Test(expected=com.capgemini.exceptions.InvalidAccountNumberException.class)
	public void whenAccountNumberIsInvalid() throws InsufficientBalanceException, InvalidAmountException, InvalidAccountNumberException {
		accountService.transferAmount(10,1,400);
	}
	
	@Test
	public void whenTransferDetailsAreValidSystemShouldDisplayExpectedOutput() throws InsufficientBalanceException, InvalidAmountException, InvalidAccountNumberException 
	{
		Account account1= new Account();
		account1.setAccountNumber(101);
		account1.setAmount(1000);
		when(accountRepository.searchAccount(101)).thenReturn(account1);
		
		
	
		Account account2=new Account();
		account2.setAccountNumber(102);
		account2.setAmount(7000);
		when(accountRepository.searchAccount(102)).thenReturn(account2);
		
	
		boolean status = accountService.transferAmount(101, 102, 100);
		assertTrue(status);
	
		}
}

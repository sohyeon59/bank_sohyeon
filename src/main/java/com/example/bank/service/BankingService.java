package com.example.bank.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.dto.TransactionDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.Member;
import com.example.bank.entity.Transaction;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.MemberRepository;
import com.example.bank.repository.TransactionRepository;

import jakarta.transaction.Transactional;

@Service
public class BankingService {

	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private TransactionRepository tranRepo;
	@Autowired
	private MemberRepository memRepo;
	
	// 계좌 생성
	public Account makeAccount(Member member) {		
		Random rand = new Random();
		int num = rand.nextInt(1000000000) + 1;		
		String accountNumber = String.format("%010d", num);
		
		while(accountRepo.existsByAccountNumber(accountNumber)) {
			num = rand.nextInt(1000000000) + 1;
			accountNumber = String.format("%010d", num);
		}
		
		Account account = Account.builder()
								 .accountNumber(accountNumber)
								 .balance(0)
								 .member(member)
								 .build();
		
		return accountRepo.save(account);
	}
	
	// 트랜잭션
	public Transaction tran(TransactionDto tranDto) {
		Transaction tran = tranDto.toEntity();
		return tranRepo.save(tran);
	}
	
	// 입금
	public Account deposit(Account account, int balance) {
		account.setBalance(account.getBalance() + balance);
		return accountRepo.save(account);
	}
	
	// 출금
	public Account withdraw(String accountNumber, int balance) {
		Account account = accountRepo.findByAccountNumber(accountNumber);
		account.setBalance(account.getBalance() - balance);
		return accountRepo.save(account);
	}
	
	// 송금(내계좌출금, 상대방계좌입금)
	@Transactional
	public void transfer(String sender, String receiver, int amount) {
		Account senderAccount = accountRepo.findByAccountNumber(sender);
		Account receiverAccount = accountRepo.findByAccountNumber(receiver);
		
		if(senderAccount.getBalance() < amount) {		
			throw new RuntimeException();				
		}
		
		withdraw(sender, amount);  // 내계좌 출금	
		deposit(receiverAccount, amount);  // 상대방 계좌 입금
		
		return;
	}
	
	
	
}

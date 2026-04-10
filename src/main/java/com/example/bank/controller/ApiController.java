package com.example.bank.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.dto.TransactionDto;
import com.example.bank.entity.Account;
import com.example.bank.entity.Member;
import com.example.bank.entity.Transaction;
import com.example.bank.jwt.JwtUtil;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.MemberRepository;
import com.example.bank.repository.TransactionRepository;
import com.example.bank.service.BankingService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiController {
	
	@Autowired
	JwtUtil util;
	@Autowired
	MemberRepository memRepo;
	@Autowired
	AccountRepository accountRepo;
	@Autowired
	TransactionRepository tranRepo;
	@Autowired
	BankingService service;
	
	
	@GetMapping("/banking/depositPage")
	public String depositPage() {
		System.out.println("/api/banking/depositPage 컨트롤러");
		return "go depositPage";
	}
	
	@GetMapping("/banking/transferPage")
	public String transferPage() {
		return "go transferPage";
	}
	
	// 입금
	@PostMapping("/banking/deposit")
	public String deposit(@RequestParam("balance") int balance,
						  HttpServletRequest request) {
		
		String token = request.getHeader("Authorization").substring(7);		
		String username = util.getUsername(token);
		Member member = memRepo.findByUsername(username);
		
		Account account = accountRepo.findByMember(member);
		service.deposit(account, balance);  // 입금
		
		// 트랜잭션
		TransactionDto tranDto = new TransactionDto();
		tranDto.setSenderAccount(account.getAccountNumber());
		tranDto.setReceiverAccount(account.getAccountNumber());
		tranDto.setAmount(balance);
		tranDto.setType("DEPOSIT");			
		service.tran(tranDto);
		
		return "success deposit";
	}
	
	// 송금
	@PostMapping("/banking/transfer")
	public String transfer(@RequestParam("receiver")String receiver,
						   @RequestParam("amount")int amount,
						   HttpServletRequest request) {
		
		String token = request.getHeader("Authorization").substring(7);		
		String username = util.getUsername(token);
		Member member = memRepo.findByUsername(username);
		Account senderAccount = accountRepo.findByMember(member);
		
		String sender = senderAccount.getAccountNumber();
		service.transfer(sender, receiver, amount);  // 송금(출금, 입금)
		
		// 트랜잭션
		TransactionDto tranDto = new TransactionDto();
		tranDto.setSenderAccount(sender);
		tranDto.setReceiverAccount(receiver);
		tranDto.setAmount(amount);
		tranDto.setType("TRANSFER");
		service.tran(tranDto);
		
		return "success transfer";
	}
	
	
	// ------------------------------------ 
	
	@GetMapping("/member/myPage")
	public Map<String, Object> myPage(HttpServletRequest request) {		

		// 잔액
		String token = request.getHeader("Authorization").substring(7);		
		String username = util.getUsername(token);
		Member member = memRepo.findByUsername(username);
		Account account  = accountRepo.findByMember(member);
		
		// 거래내역
		List<Transaction> tranList = tranRepo.findTop5BySenderAccount(account.getAccountNumber());
		
		Map<String, Object> myPage = new HashMap<>();
		myPage.put("balance", account.getBalance());
		myPage.put("tranList", tranList);
		
		return myPage;
	}
	
	@GetMapping("/admin/adminPage")
	public String adminPage() {
		return "go adminPage";
	}

	
	
	
	
	
	
}

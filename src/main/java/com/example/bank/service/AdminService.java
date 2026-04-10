package com.example.bank.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bank.dto.MemInformation;
import com.example.bank.entity.Account;
import com.example.bank.entity.Member;
import com.example.bank.repository.AccountRepository;
import com.example.bank.repository.MemberRepository;

@Service
public class AdminService {

	@Autowired
	MemberRepository memRepo;
	@Autowired
	AccountRepository accountRepo;
	
	public List<MemInformation> memList(){
		List<Member> memList = memRepo.findAll();
		List<MemInformation> memInfoList = new ArrayList<>();
		
		for(Member m : memList) {
			Account account = accountRepo.findByMember(m);
			String accountNumber = account.getAccountNumber();
			int balance = account.getBalance();
			
			MemInformation memInfo = new MemInformation(m.getId(), m.getUsername(), accountNumber, balance);
			memInfoList.add(memInfo);
		}
		
		return memInfoList;
	}
	
}

package com.example.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.bank.dto.MemberDto;
import com.example.bank.entity.Member;
import com.example.bank.repository.MemberRepository;

@Service
public class JoinService {

	@Autowired
	private MemberRepository memRepo;	
	@Autowired
	private BCryptPasswordEncoder pwEncoder;	
	@Autowired
	BankingService bankingService;
	
	
	// 회원가입을 하면서 계좌 생성
	public Member join(MemberDto memDto) {
		
		String secPW = pwEncoder.encode(memDto.getPassword());	
		Member member = memDto.toEntity();
		member.setPassword(secPW);
		
		if(memDto.getUsername().equals("admin")) {
			member.setRole("ROLE_ADMIN");
		}else {
			member.setRole("ROLE_MEMBER");
		}
		
		// 회원 저장
		Member savedMember = memRepo.save(member);
		
		// 계좌 생성
		bankingService.makeAccount(savedMember);	
		
		return savedMember;
	}
	
	
}

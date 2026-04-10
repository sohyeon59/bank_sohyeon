package com.example.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bank.entity.Account;
import com.example.bank.entity.Member;

public interface AccountRepository extends JpaRepository<Account, Long> {
	boolean existsByAccountNumber(String accountNumber);
	Account findByMember(Member member);
	Account findByAccountNumber(String accountNumber);
}

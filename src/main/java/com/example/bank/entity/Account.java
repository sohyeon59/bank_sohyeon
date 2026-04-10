package com.example.bank.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bank_account")
@Data
@NoArgsConstructor
@Builder
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(unique = true)
	private String accountNumber;
	private int balance;
	
	@ManyToOne
	@JoinColumn(name="member_id")
	private Member member;
	
	@Builder
	public Account(String accountNumber, int balance, Member member) {
		this.accountNumber = accountNumber;
		this.balance = balance;
		this.member = member;
	}

}

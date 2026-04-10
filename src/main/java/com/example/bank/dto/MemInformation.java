package com.example.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemInformation {

	private Long member_id;
	private String username;
	private String accountNumber;
	private int balance;
	
}

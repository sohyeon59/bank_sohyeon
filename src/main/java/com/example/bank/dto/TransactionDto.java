package com.example.bank.dto;

import java.time.LocalDateTime;

import com.example.bank.entity.Transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {

	private String senderAccount;
	private String receiverAccount;
	private int amount;
	private String type;
	
	public Transaction toEntity() {
		return Transaction.builder()
						  .senderAccount(senderAccount)
						  .receiverAccount(receiverAccount)
						  .amount(amount)
						  .type(type)
						  .CreatedAt(LocalDateTime.now())
						  .build();
	}
 
}

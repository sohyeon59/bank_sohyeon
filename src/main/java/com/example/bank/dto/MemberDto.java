package com.example.bank.dto;

import com.example.bank.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

	private String username;
	private String password;
	private String role;
	
	public Member toEntity() {
		return Member.builder()
					 .username(username)
					 .password(password)
					 .build();
	}
	
}

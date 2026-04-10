package com.example.bank.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="bank_mem")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	private String username;
	private String password;
	private String role;
	
}

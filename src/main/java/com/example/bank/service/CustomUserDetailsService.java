package com.example.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.bank.auth.CustomUserDetails;
import com.example.bank.entity.Member;
import com.example.bank.repository.MemberRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memRepo.findByUsername(username);
		
		if(member != null) {
			return new CustomUserDetails(member);
		}
		return null;
	}

}

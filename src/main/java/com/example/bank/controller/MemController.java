package com.example.bank.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.bank.dto.MemberDto;
import com.example.bank.entity.Member;
import com.example.bank.jwt.JwtUtil;
import com.example.bank.repository.MemberRepository;
import com.example.bank.service.JoinService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemController {

	private final JwtUtil util;
	@Autowired
	private MemberRepository memRepo;
	@Autowired
	private JoinService joinService;
	@Autowired
	BCryptPasswordEncoder PwEncoder;

	@PostMapping("/join")
	public String join(MemberDto memDto, Model model) {
		joinService.join(memDto);
		String username = memDto.getUsername();
		model.addAttribute("msg", username + "님 환영합니다.");
		return "redirect:/loginForm";
	}
	
	@ResponseBody
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> login,
													 HttpServletResponse response){
		
		String username = login.get("username");
		Member mem1 = memRepo.findByUsername(username);
		String password = mem1.getPassword();
		
		Member mem = memRepo.findByUsernameAndPassword(username, password);
		if(mem == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					 .body(Map.of("message", "아이디 또는 비밀번호가 틀렸습니다."));
		}
		
		String role = mem.getRole();
		String token = util.generateToken(username, role);
		response.setHeader("Authorization", "Bearer " + token);
		
		return ResponseEntity.ok(Map.of("token", token, "message", username + "님 반갑습니다."));
		
	}
	
	
	
	
}

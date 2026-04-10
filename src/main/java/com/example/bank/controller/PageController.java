package com.example.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.bank.dto.MemInformation;
import com.example.bank.service.AdminService;


@Controller
public class PageController {

	@Autowired
	AdminService service;
	
	@GetMapping("/")
	public String root() {
		return "index";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "join";
	}
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "login";
	}
	
	@GetMapping("/notice")
	public String notice() {
		return "notice";
	}

	// --------------------------------------------------
	
	@GetMapping("/banking/depositPage")
	public String depositPage() {
		return "deposit";
	}
	
	@GetMapping("/banking/transferPage")
	public String transferPage() {
		return "transfer";
	}
		
	// ---------------------------------------------

	@GetMapping("/member/myPage")
	public String myPage() {		
		return "myPage";
	}
	
	@GetMapping("/admin/adminPage")
	public String adminPage(Model model) {
		List<MemInformation> memInfoList = service.memList();		
		model.addAttribute("memInfoList", memInfoList);
		return "adminPage";
	}
	
	
	
}

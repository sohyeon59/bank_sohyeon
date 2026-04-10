package com.example.bank.jwt;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil util;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String uri = request.getRequestURI();
		System.out.println(uri);
		
//		if(!uri.startsWith("/api/")) {
//			filterChain.doFilter(request, response);
//			return;
//		}
		
		String header = request.getHeader("Authorization");
		System.out.println("header: " + header);
		
		if(header == null || !header.startsWith("Bearer ")) {
			System.out.println("--------- token null check ----------");
			filterChain.doFilter(request, response);
			return;
		}
		System.out.println("--------- token exist ----------");
		
		String token = header.substring(7);
		if(!util.isValid(token)) {
			System.out.println("------------ token valid check --------");
			filterChain.doFilter(request, response);
			return;
		}

		System.out.println("--------- token usable ----------");
		
		String username = util.getUsername(token);
		String role = util.getRole(token);
		
		List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
		
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(username, null, authorities);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
//		System.out.println("role: " + role);
//		System.out.println("authorities: " + authorities);
//		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		
		System.out.println(("filter success"));
		
		filterChain.doFilter(request, response);		
	}

}

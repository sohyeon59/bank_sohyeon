package com.example.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.bank.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public BCryptPasswordEncoder pwEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.sessionManagement(session -> 
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.csrf(csrf -> csrf.disable());		
		http.formLogin(login -> login.disable());
		http.httpBasic(AbstractHttpConfigurer::disable);
		
		http.authorizeHttpRequests(auth -> auth
							.requestMatchers("/", "/joinForm", "/loginForm", "/join", "/login", "/notice", "/banking/**", "/member/**", "/admin/**").permitAll()
							.requestMatchers("/api/banking/**").hasAnyRole("MEMBER", "ADMIN")
							.requestMatchers("/api/member/**").hasRole("MEMBER")
							.requestMatchers("/api/admin/**").hasRole("ADMIN")
							.anyRequest().authenticated()
//							.anyRequest().permitAll()
				);
		
		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
		
	}
	
	
	
}

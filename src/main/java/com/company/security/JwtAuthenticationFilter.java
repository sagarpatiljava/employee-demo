package com.company.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationFilter() {
		super("/v1/**");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("JwtAuthenticationFilter:Attempt Authentication");
		String authHeader =request.getHeader("Authorization");
		System.out.println("Token : "+authHeader);
		if(authHeader==null) {
			throw new RuntimeException("Authorization token is missing");
			
		}
		
		
		String token = authHeader.substring(7);
		JwtAuthenticationToken authToken = new JwtAuthenticationToken(token);
		return getAuthenticationManager().authenticate(authToken);
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("JwtAuthenticationFilter:successfulAuthentication");
		super.successfulAuthentication(request, response, chain, authResult);
		//chain.doFilter(request, response);
	}
}

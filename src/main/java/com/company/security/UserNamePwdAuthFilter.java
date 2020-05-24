package com.company.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class UserNamePwdAuthFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	MyUserDetailsService userDetailService;
	
	@Override
	public void setFilterProcessesUrl(String filterProcessesUrl) {
		// TODO Auto-generated method stub
		super.setFilterProcessesUrl("/token/**");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("UserNamePwdAuthFilter :attemptAuthentication");
		UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
		
	}

	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("UserNamePwdAuthFilter :successfulAuthentication");
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}
	private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
	
		
		   String username = request.getHeader(SPRING_SECURITY_FORM_USERNAME_KEY);
	        String password = request.getHeader(SPRING_SECURITY_FORM_PASSWORD_KEY);
	   
	 
	       return new UsernamePasswordAuthenticationToken(
	        		username, password);
		
	}
}

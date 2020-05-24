package com.company.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	private String secret = "employee";
	
	@Autowired
	private JwtValidator jwtValidator ;
	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		System.out.println("JwtAuthenticationProvider : supports");
		return JwtAuthenticationToken.class.isAssignableFrom(authentication);
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
         System.out.println("JwtAuthenticationProvider : retrieveUser");
		JwtAuthenticationToken jwtToken =  (JwtAuthenticationToken)authentication;
		String token = jwtToken.getToken();
		if(jwtValidator.isTokenExpired(jwtValidator.ExtractClaim(token, secret))) {
			System.out.println("Token is expired");

		}
		JwtUser user = jwtValidator.validate(token,secret) ;
		if(user == null ) {
			System.out.println("Can not be validated");
		}
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(user.getRole());
		
		JwtUserDetail userDetails = new JwtUserDetail(user.getUserName(),user.getId(),token,authorities);
		
		return userDetails;
	}
	

}

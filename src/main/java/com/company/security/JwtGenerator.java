package com.company.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {

	public String generate(JwtUser jwtUser) {
		// TODO Auto-generated method stub
		Claims claims = Jwts.claims()
				.setSubject(jwtUser.getUserName())
				.setId(jwtUser.getId());
		claims.put("role", jwtUser.getRole());
		
	return	Jwts.builder().setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis()+1000*60*5))
			.signWith(SignatureAlgorithm.HS512, "employee")
		.compact();
				
				
	
	}

}

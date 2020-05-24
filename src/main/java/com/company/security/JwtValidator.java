package com.company.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtValidator {

	public JwtUser validate(String token, String secret) {
		// TODO Auto-generated method stub
		System.out.println("JwtValidator : validateToken");
		System.out.println("Received token : " + token);
		JwtUser jwtUser = null;
		try {
			Claims body = ExtractClaim(token, secret);

			jwtUser = new JwtUser();
			System.out.println(" after parsing :");
			System.out.println("Subject :" + body.getSubject());
			System.out.println("Id : " + body.getId());
			System.out.println("Role :" + body.get("role"));
			jwtUser.setUserName(body.getSubject());
			jwtUser.setId(body.getId());
			jwtUser.setRole(body.get("role", String.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jwtUser;

	}

	public boolean isTokenExpired(Claims claim) {
		return claim.getExpiration().before(new Date());
	}

	public Claims ExtractClaim(String token, String secret) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

}

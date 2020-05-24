package com.company.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.security.JwtGenerator;
import com.company.security.JwtUser;
@RestController
@RequestMapping("/token")
public class SecurityController {

	
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generateToken(@RequestBody JwtUser  jwtUser) {
	
		JwtGenerator tokenGen= new JwtGenerator();
		
		
		return tokenGen.generate(jwtUser);
	}
}

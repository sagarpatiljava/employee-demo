package com.company.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService{
	@Autowired
	PasswordEncoder passwordEncoder;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		JwtUserDetail userDetails = new JwtUserDetail();
		switch(username) {
		case "jayshree" : 			
		return new JwtUserDetail("jayshree",passwordEncoder.encode("jayshree123"),Collections.emptyList());
		case "sagar" : 			
		return new JwtUserDetail("sagar",passwordEncoder.encode("sagar123"),Collections.emptyList());
	
		}
		return userDetails;
		}

}

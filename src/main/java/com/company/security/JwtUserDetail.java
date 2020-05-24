package com.company.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetail implements UserDetails{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userName;
	private String password;
	private String id;
	private String token;
	private List<GrantedAuthority> authorities;
	
	JwtUserDetail(String userName, String id, String token, List<GrantedAuthority> authorities){
	this.userName = userName;
	this.id=id;
	this.token=token;
	this.authorities=authorities;
	
	}
	
	JwtUserDetail(String userName, String password,List<GrantedAuthority> authorities){
		this.userName = userName;
		this.password=password;
		this.authorities=authorities;
		
		}
	



	
	public JwtUserDetail() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}



	public String getToken() {
		return token;
	}





	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.authorities;
	}

	

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}
	
}

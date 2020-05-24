package com.company.security;

public class JwtUser {

	String userName ;
	String id ;
	String role;
	public void setUserName(String string) {
		// TODO Auto-generated method stub
		this.userName=string;
	}

	public void setId(String object) {
		// TODO Auto-generated method stub
		this.id=object;
	}

	public void setRole(String string) {
		// TODO Auto-generated method stub
		this.role=string;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getId() {
		return this.id;
	}

	public String getRole() {
		return this.role;
	}
	

}

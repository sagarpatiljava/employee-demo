package com.company.model;

public class Address {

	private Integer zipCode;
	private String street;
	private String city;
	private String state;
	
	
	@Override
	public String toString() {
		return "Address [zipCode=" + zipCode + ", street=" + street + ", city=" + city + ", state=" + state + "]";
	}
	public Address(Integer zipCode, String street, String city, String state) {
		super();
		this.zipCode = zipCode;
		this.street = street;
		this.city = city;
		this.state = state;
	}
	public Integer getZipCode() {
		return zipCode;
	}
	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}

package com.company.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.model.SystemMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



@Entity
@Table(name = "employee")
@JsonInclude(Include.NON_NULL)
public class Employee implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -758119228182036765L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employeeid")
	private Integer employeeID;
	@Column(name = "employeename")
	private String employeeName;
	
	@ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinColumn(name = "dept_Id")
	@JsonIgnoreProperties("employees")
	private Department department;
	
	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "addressid", referencedColumnName = "addressid")
	private Address address;
	
	@Transient
	private SystemMessage systemMessage;
	
	
	public Employee() {
		
	}
	public Employee(String employeeName, Department department, Address address) {
		super();
		this.employeeName = employeeName;
		this.department = department;
		this.address = address;
	}

	public Employee(String message , String code) {
		super();
		this.systemMessage=new SystemMessage(message,code);
		
		}
	public Integer getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(Integer employeeID) {
		this.employeeID = employeeID;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	public SystemMessage getSystemMessage() {
		return systemMessage;
	}
	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
	}

}

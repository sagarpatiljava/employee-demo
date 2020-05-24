package com.company.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.company.model.SystemMessage;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "department")
@JsonInclude(Include.NON_NULL)
//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class Department implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7236843458165026589L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "deptid")
	private Integer deptId;
	@Column(name = "deptname")
	private String deptName;
	@Column(name = "depthead")
	private String deptHead;
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
	@JsonIgnoreProperties("department")
	private List<Employee> employees;

	@Transient
	private SystemMessage systemMessage;
	
	public Department() {
		
	}
	
	public Department(String deptName, String deptHead, List<Employee> employees) {
		super();
	
		this.deptName = deptName;
		this.deptHead = deptHead;
		this.employees = employees;
	}

	public Department(String message , String code) {
		super();
		this.systemMessage=new SystemMessage(message,code);
		
		}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptHead() {
		return deptHead;
	}

	public void setDeptHead(String deptHead) {
		this.deptHead = deptHead;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	public SystemMessage getSystemMessage() {
		return systemMessage;
	}
	public void setSystemMessage(SystemMessage systemMessage) {
		this.systemMessage = systemMessage;
	}

}

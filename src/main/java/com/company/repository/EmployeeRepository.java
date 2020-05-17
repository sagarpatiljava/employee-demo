package com.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.company.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}

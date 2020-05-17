package com.company.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.company.entity.Department;
import com.company.entity.Employee;
import com.company.repository.DepartmentRepository;
import com.company.repository.EmployeeRepository;

@RestController
@RequestMapping("v1")
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	DepartmentRepository departmentRepository;

	@RequestMapping(value = "/Employees/{id}", method = RequestMethod.GET)
	public ResponseEntity<Employee> searchEmployeeById(@PathVariable(name = "id") Integer employeeID) {
		Employee emp = employeeRepository.findById(employeeID).orElse(new Employee());
		if (emp == null)
			return new ResponseEntity<Employee>(new Employee("No Employee found with id "+employeeID,"404_NOT_FOUND"), HttpStatus.NOT_FOUND);

		return new ResponseEntity<Employee>(emp, HttpStatus.OK);

	}

	@RequestMapping(value = "/Employees", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> searchEmployees() {
		List<Employee> result = employeeRepository.findAll();
		if (CollectionUtils.isEmpty(result))
			return new ResponseEntity<List<Employee>>(Arrays.asList(new Employee("No Employees present","404_NOT_FOUND")), HttpStatus.NOT_FOUND);

		return new ResponseEntity<List<Employee>>(result, HttpStatus.OK);

	}

	@RequestMapping(value = "/Departments/{id}", method = RequestMethod.GET)
	public ResponseEntity<Department> searchDeptById(@PathVariable(name = "id") Integer deptID) {
		Department dept = departmentRepository.findById(deptID).orElse(null);
		if (dept == null) {
			return new ResponseEntity<Department>(new Department("No department with Id "+deptID,"404_NOT_FOUND"), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Department>(dept, HttpStatus.OK);
	}

	@RequestMapping(value = "/Departments", method = RequestMethod.GET)
	public ResponseEntity<List<Department>> searchDepartments() {
		List<Department> depts = departmentRepository.findAll();
		if (depts == null)
			return new ResponseEntity<List<Department>>(Arrays.asList(new Department("No department present","404_NOT_FOUND")), HttpStatus.NOT_FOUND);
		return new ResponseEntity<List<Department>>(depts, HttpStatus.OK);
	}

	@RequestMapping(value = "/Departments/{deptid}/Employees", method = RequestMethod.GET)
	public ResponseEntity<List<Employee>> searchEmpInDepBydeptId(@PathVariable(name = "deptid") Integer deptID) {
		List<Employee> result = departmentRepository.findById(deptID).map(x -> x.getEmployees())
				.orElse(Arrays.asList(new Employee()));
		if (CollectionUtils.isEmpty(result))
			return new ResponseEntity<List<Employee>>(Arrays.asList(new Employee("No employees under dept "+deptID,"404_NOT_FOUND")), HttpStatus.NOT_FOUND);

		return new ResponseEntity<List<Employee>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/Employees/{empid}/Department", method = RequestMethod.GET)
	public ResponseEntity<Department> searchDepOfEmpId(@PathVariable(name = "empid") Integer empID) {
		Department dept = employeeRepository.findById(empID).map(x -> x.getDepartment()).orElse(new Department());
		if (dept == null) {
			return new ResponseEntity<Department>(new Department("Employee does not have department","204_NOT_CONTENT"), HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Department>(dept, HttpStatus.OK);
	}

	@RequestMapping(value = "/Employees", method = RequestMethod.POST)
	public ResponseEntity<List<Employee>> addEmployees(@RequestBody List<Employee> employees) {

		employees.stream().forEach(x -> updateDepartment(x));
		List<Employee> resultList = employeeRepository.saveAll(employees);
		return new ResponseEntity<List<Employee>>(resultList, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/Departments", method = RequestMethod.POST)
	public ResponseEntity<Department> addDepartments(@RequestBody Department department) {
		if (department.getDeptId() != null || department.getEmployees() != null) {
			
			return new ResponseEntity<Department>(
					new Department("DepartmentID/Employees should be null","404_BAD_REQUEST"),
					HttpStatus.BAD_REQUEST);
		}
		department = departmentRepository.saveAndFlush(department);

		return new ResponseEntity<Department>(department, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/Employees/{empid}", method = RequestMethod.PUT)
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee,
			@PathVariable(name = "empid") Integer empID) {

		Employee emp = employeeRepository.findById(empID).orElse(null);
		if (emp == null) {
			return new ResponseEntity<Employee>(new Employee("No Employee found with id "+empID,"400_BAD_REQUEST"), HttpStatus.BAD_REQUEST);
		}
		if(employee.getAddress()!=null)
		emp.setAddress(employee.getAddress());
		boolean updated=updateDepartment(employee);
		 if(updated)
			emp.setDepartment(employee.getDepartment());
		
		emp.setEmployeeName(employee.getEmployeeName());
		Employee result = employeeRepository.saveAndFlush(emp);
		return new ResponseEntity<Employee>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/Departments/{deptid}", method = RequestMethod.PUT)
	public ResponseEntity<Department> updateDepartment(@RequestBody Department department,
			@PathVariable(name = "deptid") Integer deptID) {

		Department dept = departmentRepository.findById(deptID).orElse(null);
		if (dept == null) {
			return new ResponseEntity<Department>(
					new Department("No department found for "+deptID,"404_NOT_FOUND"),
					HttpStatus.NOT_FOUND);
		}
		if(!CollectionUtils.isEmpty(department.getEmployees()))
			return new ResponseEntity<Department>(new Department("Employees can be updated","400_BAD_REQUEST"),HttpStatus.BAD_REQUEST);
		if(department.getDeptHead()!=null)
		dept.setDeptHead(department.getDeptHead());
		if(department.getDeptName()!=null)
		dept.setDeptName(department.getDeptName());
		
		Department result = departmentRepository.saveAndFlush(dept);
		return new ResponseEntity<Department>(result, HttpStatus.OK);

	}

	@RequestMapping(value = "/Employees/{empid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteEmployee(@PathVariable(name = "empid") Integer empID) {
		if(employeeRepository.findById(empID).isPresent()) {
			employeeRepository.deleteById(empID); 
			return new ResponseEntity<String>("Deleted", HttpStatus.ACCEPTED);
			}
	
		return new ResponseEntity<String>("No Resource", HttpStatus.NO_CONTENT);
		
	}

	@RequestMapping(value = "/Departments/{deptid}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteDeppartment(@PathVariable(name = "deptid") Integer deptid) {

		Department dept = departmentRepository.findById(deptid).orElse(null);
		if (dept != null && !CollectionUtils.isEmpty(dept.getEmployees())) {
			return new ResponseEntity<String>("Departent have at least 1 employee.", HttpStatus.NOT_ACCEPTABLE);
		}
		if (dept == null) {
			return new ResponseEntity<String>("No department found", HttpStatus.NOT_FOUND);
		}
		departmentRepository.deleteById(deptid);
		return new ResponseEntity<String>("Deleted", HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/Departments/{deptid}/Employees", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteEmployeesOfDepartment(@PathVariable(name = "deptid") Integer deptid) {

		Department dept = departmentRepository.findById(deptid).orElse(null);
		if (dept != null ) {
			if( !CollectionUtils.isEmpty(dept.getEmployees())) {
			employeeRepository.deleteAll(dept.getEmployees());
			return new ResponseEntity<String>("Deleted", HttpStatus.NOT_ACCEPTABLE);
			}
			return new ResponseEntity<String>("No employees found", HttpStatus.NOT_FOUND);
		}
		
			return new ResponseEntity<String>("No department found", HttpStatus.NOT_FOUND);
		
	}

	private boolean updateDepartment(Employee e) {

		boolean updated=false;
		if (e.getDepartment() != null && e.getDepartment().getDeptId() != null) {

			Department dept = departmentRepository.findById(e.getDepartment().getDeptId()).orElse(null);
			if (dept != null) {
				e.setDepartment(dept);
				updated=true;
			}
		
		}
		return updated;
	}
}

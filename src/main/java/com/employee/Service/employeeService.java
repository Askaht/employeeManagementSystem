package com.employee.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.Employee.Employee;
import com.employee.Exception.EmployeeNotFoundException;
import com.employee.Repository.EmployeeRepository;

@Service
public class employeeService {

    @Autowired
    private EmployeeRepository repository;

    // Save Employee with Salary Calculation
    public Employee saveEmployee(Employee emp) {

        if (emp.getSalary() <= 0) {
            throw new RuntimeException("Salary must be greater than 0");
        }

        double basicSalary = emp.getSalary();
        double hra = basicSalary * 0.30;
        double da  = basicSalary * 0.05;
        double pf  = basicSalary * 0.04;   // ✅ fixed

        double newSalary = basicSalary + hra + da - pf;
        emp.setSalary(newSalary);

        return repository.save(emp);
    }

    // Delete Employee by id
    public void deleteEmployee(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
    }

    // Save all employees
    public List<Employee> saveAllEmployees(List<Employee> employees) {
        return repository.saveAll(employees);
    }

    // Get employee by id
    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not available with ID: " + id));
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Delete all employees
    public void deleteAllEmployees() {

        if (repository.count() == 0) {
            throw new EmployeeNotFoundException("No employees present to delete");
        }
        repository.deleteAll();
    }

    // PUT – Full update
    public Employee updateEmployee(Employee employee) {

        Employee existing = repository.findById(employee.getId())
                .orElseThrow(() ->
                        new EmployeeNotFoundException(
                                "Employee not found with id " + employee.getId()));

        // ✅ update fields
        existing.setName(employee.getName());
        existing.setSalary(employee.getSalary());
        existing.setEmail(employee.getEmail());
        existing.setMobile(employee.getMobile());

        return repository.save(existing);
    }

    @Transactional
    public Employee patchEmployee(Long id, Map<String, Object> updates) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Employee not found with id " + id));

        for (Map.Entry<String, Object> entry : updates.entrySet()) {

            String key = entry.getKey();
            Object value = entry.getValue();

            switch (key) {

                case "name":
                    employee.setName((String) value);
                    break;

                case "email":
                    employee.setEmail((String) value);
                    break;

                case "salary":
                    employee.setSalary(
                            Double.parseDouble(value.toString()));
                    break;

                case "mobile":
                    employee.setMobile(
                            Long.parseLong(value.toString()));
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Invalid field: " + key);
            }
        }

        return repository.save(employee);
    }


     //This is for 
	public  List<Employee> filterByRange(double minSalary, double maxSalary) {
		List<Employee> employee=repository.findBySalaryBetween(minSalary,maxSalary);
		return employee;
	}
 //This is for filter By LessThan
	public  List<Employee> filterByLessThan(double minSalary) {
	
		List<Employee> employee= repository.findBySalaryLessThan( minSalary );
		return employee;
	}
	// This is for filter By GreaterThan
	public  List<Employee> filterByGreaterThan(double maxSalary) {
		
		List<Employee> employee= repository.findBySalaryGreaterThan( maxSalary );
		return employee;
	}

	//********************************************************************************************************************
	
	
	// This is get Employee By Email
	  public Employee getEmployeeByEmail(String email) {
	        return repository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("Employee not found"));
	    }
	  // This is for get Employees By Salary Or Gender
	    public List<Employee> getEmployeesBySalaryOrGender(double salary, String gender) {
	        return repository.findBySalaryGreaterThanOrGender(salary, gender);
	    }

	    //This is for get Employees By Gender And Salary
	    public List<Employee> getEmployeesByGenderAndSalary(String gender, double minSalary) {
	        return repository.findByGenderAndSalaryGreaterThan(gender, minSalary);
	    }
 
	    // This is for get Employees By Gender Or Salary Between
	    public List<Employee> getEmployeesByGenderOrSalaryBetween(
	            String gender, double minSalary, double maxSalary) {
	        return repository.findByGenderOrSalaryBetween(gender, minSalary, maxSalary);
	    }

	    //This is for getEmployeesOrderBySalaryAsc
	    public List<Employee> getEmployeesOrderBySalaryAsc() {
	        return repository.findAllByOrderBySalaryAsc();
	    }

	    // This is for getEmployeesOrderBySalaryDesc...
	    public List<Employee> getEmployeesOrderBySalaryDesc() {
	        return repository.findAllByOrderBySalaryDesc();
	    }

	    //This is for  getEmployeesByNameIgnoreCase...
	    public List<Employee> getEmployeesByNameIgnoreCase(String name) {
	        return repository.findByNameIgnoreCase(name);
	    }
	    
	    //This is for search by name
	    public List<Employee> searchEmployeesByName(String keyword) {
	        return repository.findByNameContaining(keyword);
	    }

	    //Delete Employee By Salary Between
		public void deleteEmployeeBySalaryRange(double minSalary, double maxSalary) {
			// TODO Auto-generated method stub
			   repository.deleteEmployeeBySalaryRange(minSalary,maxSalary);
		}
       
		//Delete Employee By Email 
		public void deleteEmployeeByEmail(String email) {
			// TODO Auto-generated method stub
			 repository.deleteByEmail(email);
			
		}
	}
	




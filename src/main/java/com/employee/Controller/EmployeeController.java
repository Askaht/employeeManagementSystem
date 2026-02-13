package com.employee.Controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.Employee.Employee;
import com.employee.Exception.EmployeeNotFoundException;
import com.employee.Service.employeeService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private employeeService service;   // ✅ FIXED

    // Save single employee
    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee emp) {
        Employee savedEmployee = service.saveEmployee(emp);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    // Save multiple employees
    @PostMapping("/bulk")
    public ResponseEntity<List<Employee>> saveAllEmployees(
            @RequestBody List<Employee> employees) {

        List<Employee> savedEmployees = service.saveAllEmployees(employees);
        return new ResponseEntity<>(savedEmployees, HttpStatus.CREATED);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees1() {

        List<Employee> employees = service.getAllEmployees();

        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "All employee data retrieved")
                .body(employees);
    }

    // Get employee by ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable Long id) throws EmployeeNotFoundException {

        Employee employee = service.getEmployeeById(id); // ✅ FIXED

        return ResponseEntity.status(HttpStatus.OK)
                .header("info", "Data is retrieved")
                .body(employee);
    }

    // Delete employee by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) throws EmployeeNotFoundException {

        service.deleteEmployee(id);   // ✅ FIXED
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Delete all employees
    @DeleteMapping
    public ResponseEntity<Void> deleteAllEmployee() {

        service.deleteAllEmployees();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // PUT – update employee
    @PutMapping
    public ResponseEntity<Employee> updateEmployee(
            @RequestBody Employee employee) {

        Employee updated = service.updateEmployee(employee);
        return ResponseEntity.ok(updated);
    }

    // PATCH – partial update
    @PatchMapping("/{id}")
    public ResponseEntity<Employee> patchEmployee(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        Employee updated = service.patchEmployee(id, updates);
        return ResponseEntity.ok(updated);
    }
    
    
    
    @GetMapping("/filterByrange-max")
    public ResponseEntity<List<Employee>> filterByRange(
            @RequestParam double maxSalary) {

        List<Employee> filterByGreaterThan = service.filterByGreaterThan(maxSalary);

       return ResponseEntity.ok(filterByGreaterThan);
    }
     
    
    //LessThen..........................
    
    @GetMapping("/filterbyrange-min")
    public ResponseEntity<List<Employee>> filterByLessThen(
            @RequestParam double minSalary){
    	List<Employee> filterByLessThan = service.filterByLessThan(minSalary);

        return ResponseEntity.ok(filterByLessThan);
    }

   

    //email***************************************************************************************************email*******************************************************************
    @GetMapping("/email/{email}")
    public Employee getByEmail(@PathVariable String email) {
        return service.getEmployeeByEmail(email);
    }

    @GetMapping("/salary-or-gender")
    public List<Employee> salaryOrGender(
            @RequestParam double salary,
            @RequestParam String gender) {
        return service.getEmployeesBySalaryOrGender(salary, gender);
    }

    @GetMapping("/gender-and-salary")
    public List<Employee> genderAndSalary(
            @RequestParam String gender,
            @RequestParam double minSalary) {
        return service.getEmployeesByGenderAndSalary(gender, minSalary);
    }

    @GetMapping("/gender-or-salary-between")
    public List<Employee> genderOrSalaryBetween(
            @RequestParam String gender,
            @RequestParam double minSalary,
            @RequestParam double maxSalary) {
        return service.getEmployeesByGenderOrSalaryBetween(
                gender, minSalary, maxSalary);
    }

    @GetMapping("/sort/salary/asc")
    public List<Employee> sortSalaryAsc() {
        return service.getEmployeesOrderBySalaryAsc();
    }

    @GetMapping("/sort/salary/desc")
    public List<Employee> sortSalaryDesc() {
        return service.getEmployeesOrderBySalaryDesc();
    }

    @GetMapping("/name/{name}")
    public List<Employee> getByNameIgnoreCase(@PathVariable String name) {
        return service.getEmployeesByNameIgnoreCase(name);
    }

    @GetMapping("/search")
    public List<Employee> searchByName(@RequestParam String keyword) {
        return service.searchEmployeesByName(keyword);
    }
    
    

    @DeleteMapping("/deletebyrange")
    public ResponseEntity<Void> deleteEmployeeBySalaryRange(
            @RequestParam double minSalary,
            @RequestParam double maxSalary) {

        service.deleteEmployeeBySalaryRange(minSalary, maxSalary);
        return ResponseEntity.noContent().build();
    }

    
    @Operation(summary = "Delete By Email")
    @DeleteMapping("/deletebyemail")
    public ResponseEntity<Void> deleteEmployeeByEmail(@RequestParam String email) {

        service.deleteEmployeeByEmail(email);
        return ResponseEntity.noContent().build();
    }

    
    
    
    
    
    
    
    
    //demo********************************************************************************************
    
   
//

//    public ResponseEntity<EntityModel<Employee>> saveEmployee(@RequestBody Employee employee) {
//
//        List<Employee> employees = service.saveEmployeeDate();
//
//        List<EntityModel<Employee>> employeeModels = employees.stream()
//                .map(employee -> EntityModel.of(employee,
//                        linkTo(methodOn(EmployeeController.class)
//                                .getEmployeeById(employee.getId()))
//                                .withSelfRel()))
//                .toList();
//
//        CollectionModel<EntityModel<Employee>> collectionModel =
//                CollectionModel.of(employeeModels,
//                        linkTo(methodOn(EmployeeController.class)
//                                .getAllEmployees())
//                                .withSelfRel());

//        return ResponseEntity.ok()
//                .header("info", "All employee data retrieved")
//                .body(collectionModel);
//    }
//
//	private employeeService methodOn(Class<EmployeeController> class1) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}

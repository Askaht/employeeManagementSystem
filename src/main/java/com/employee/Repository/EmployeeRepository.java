package com.employee.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.employee.Employee.Employee;

import jakarta.transaction.Transactional;


public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	public Optional<Employee> findByEmail(String email);
    // salary > value
    List<Employee> findBySalaryGreaterThan(double maxSalary);

    // salary < value
    List<Employee> findBySalaryLessThan(double minSalary);

    // salary between range
    List<Employee> findBySalaryBetween(double minSalary, double maxSalary);
    //**************************************************************************************************************************************
    
   

    // AND / OR conditions
    List<Employee> findBySalaryGreaterThanOrGender(double maxSalary, String gender);

    List<Employee> findByGenderAndSalaryGreaterThan(String gender, double mixSalary);

    List<Employee> findByGenderOrSalaryBetween(String gender, double minSalary, double maxSalary);

    // Sorting
    List<Employee> findAllByOrderBySalaryAsc();

    List<Employee> findAllByOrderBySalaryDesc();

    // String operations
    List<Employee> findByNameIgnoreCase(String name);

    List<Employee> findByNameContaining(String keyword);
    
    //Delete By Email
    @Modifying
    @Transactional
    @Query("DELETE FROM Employee e WHERE e.email = :email")
    void deleteByEmail(@Param("email") String email);
    
    //Delete Employee by Salary Between
    @Modifying
    @Transactional
    @Query("DELETE FROM Employee e WHERE e.salary BETWEEN :minSalary AND :maxSalary")
    void deleteEmployeeBySalaryRange(
            @Param("minSalary") double minSalary,
            @Param("maxSalary") double maxSalary
    );

    
    
    //****************************************************************************************************************************************************
    
    @Query("""
    		SELECT e FROM Employee e
    		WHERE (:email IS NULL OR e.email = :email)
    		AND (:gender IS NULL OR e.gender = :gender)
    		AND (:name IS NULL OR LOWER(e.name) = LOWER(:name))
    		AND (:keyword IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')))
    		AND (:minSalary IS NULL OR e.salary >= :minSalary)
    		AND (:maxSalary IS NULL OR e.salary <= :maxSalary)
    		ORDER BY e.salary ASC
    		""")
    		List<Employee> searchEmployees(
    		        @Param("email") String email,
    		        @Param("gender") String gender,
    		        @Param("name") String name,
    		        @Param("keyword") String keyword,
    		        @Param("minSalary") Double minSalary,
    		        @Param("maxSalary") Double maxSalary
    		);

}

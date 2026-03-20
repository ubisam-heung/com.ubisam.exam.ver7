package com.ubisam.exam.api.employees;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ubisam.exam.domain.Employee;
import java.util.List;


public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>{

  List<Employee> findByName(String name);
  List<Employee> findByEmail(String email);
  List<Employee> findBySalary(Integer salary);
  
}

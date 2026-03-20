package com.ubisam.exam.api.employees;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Employee;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class EmployeeDocs extends MockMvcRestDocs{

  //목적: 이름, 이메일, 연봉을 사용하여 테스트하기 위한 메소드
  public Employee newEntity(String... entity) {
    Employee employeeEntity = new Employee();
    employeeEntity.setName(entity.length > 0 ? entity[0] : super.randomText("name"));
    employeeEntity.setEmail(entity.length > 1 ? entity[1] : super.randomText("email"));
    employeeEntity.setSalary(entity.length > 2 ? Integer.valueOf(entity[2]) : super.randomInt());
    employeeEntity.setHireDate(String.valueOf(super.randomInt()));
    employeeEntity.setTerminationDate(String.valueOf(super.randomInt()));
    return employeeEntity;
  }
  
  //목적: 이름 변경 후 테스트를 위한 메소드
  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }

}

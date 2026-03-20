package com.ubisam.exam.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_employee")
public class Employee {
  
  //테스트할 필드만 선언
  @Id
  @GeneratedValue
  private Long id;
  //사원이름
  private String name;
  //사원 이메일
  private String email;
  //사원 연봉
  private Integer salary;
  //사원 입사일
  private String hireDate;
  //사원 퇴사일
  private String terminationDate;
  //사원 부서
  //여려명의 사원이 1개의 부서에 속할 수 있음.
  // @ManyToOne
  // private Department department;

}

package com.ubisam.exam.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_department")
public class Department {

  //테스트할 필드만 선언
  @Id
  @GeneratedValue
  private Long id;
  //부서명
  private String deptName;
  //부서장
  private String manager;
  //1개의 부서에는 여러명의 사원이 들어올 수 있음.
  // @OneToMany
  // private List<Employee> employees;

}

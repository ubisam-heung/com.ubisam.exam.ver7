package com.ubisam.exam.api.departments;

import com.ubisam.exam.domain.Department;

import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface DepartmentRepository extends RestfulJpaRepository<Department, Long>{
  
}

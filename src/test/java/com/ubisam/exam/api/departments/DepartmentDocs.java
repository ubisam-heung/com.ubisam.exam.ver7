package com.ubisam.exam.api.departments;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ubisam.exam.domain.Department;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class DepartmentDocs extends MockMvcRestDocs{

  //목적: 부서명, 부서장 필드를 사용하여 테스트하기 위한 메소드
  public Department newEntity(String... entity) {
    Department departmentEntity = new Department();
    departmentEntity.setDeptName(entity.length > 0 ? entity[0] : super.randomText("dept"));
    departmentEntity.setManager(entity.length > 1 ? entity[1] : super.randomText("manager"));
    return departmentEntity;
  }
  
  //목적: 부서명 변경 후 테스트를 위한 메소드
  public Map<String, Object> updateEntity(Map<String, Object> entity, String name){
    entity.put("name", name);
    return entity;
  }

  //목적: 키워드를 통한 검색
  public Map<String, Object> setKeyword(String keyword){
    Map<String, Object> entity = new HashMap<>();
    entity.put("keyword", keyword);
    return entity;
  }

}

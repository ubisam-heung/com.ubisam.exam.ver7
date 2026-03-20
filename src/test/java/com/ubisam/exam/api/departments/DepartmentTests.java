package com.ubisam.exam.api.departments;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.web.servlet.MockMvc;

import com.ubisam.exam.domain.Department;
import com.ubisam.exam.domain.Employee;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentTests {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private DepartmentDocs docs;

  @Autowired
  private DepartmentRepository departmentRepository;

  // Crud 테스트 - 부서명
  @Test
  void contextLoads() throws Exception{

    // Crud - C
    mvc.perform(post("/api/departments").content(docs::newEntity, "연구소"))
    .andDo(print()).andExpect(is2xx()).andDo(result(docs::context, "entity1"));

    //Crud - R
    String url = docs.context("entity1", "$._links.self.href");
    mvc.perform(post(url)).andExpect(is2xx());

    //Crud - U
    Map<String, Object> body = docs.context("entity1", "$");
    mvc.perform(put(url).content(docs::updateEntity, body, "로봇자동화")).andExpect(is2xx());

    //Crud - D
    mvc.perform(delete(url)).andExpect(is2xx());

  }

  //Handler 테스트
  @Test
  void contextLoads2() throws Exception{
    Specification<Department> spec;
		List<Department> result;
		boolean hasResult;

		//40개 엔티티 추가
		List<Department> departmentLists = new ArrayList<>(); 
		for ( int i = 1; i <= 40; i++){
			departmentLists.add(docs.newEntity("연구소" + i, "길동"+i));
		}
		departmentRepository.saveAll(departmentLists);

    //1) 부서명으로 검색
    JpaSpecificationBuilder<Department> query = JpaSpecificationBuilder.of(Department.class);
		spec = query.where().and().eq("deptName", "연구소6").build();
		result = departmentRepository.findAll(spec);
    //쿼리 결과 행 1개를 찾고 그 행의 deptName필드가 "연구소6" 인지 검사
    hasResult = result.stream().anyMatch(u -> "연구소6".equals(u.getDeptName()));
    assertEquals(true, hasResult);

    //2) 부서장명으로 검색
    JpaSpecificationBuilder<Department> query1 = JpaSpecificationBuilder.of(Department.class);
		spec = query1.where().and().eq("manager", "길동8").build();
		result = departmentRepository.findAll(spec);
    //쿼리 결과 행 1개를 찾고 그 행의 manager필드가 "길동8" 인지 검사
    hasResult = result.stream().anyMatch(u -> "길동8".equals(u.getManager()));
    assertEquals(true, hasResult);
  }

  //Search 테스트
  @Test
  void contextLoads3() throws Exception{

    //40개 엔티티 추가
		List<Department> departmentLists = new ArrayList<>(); 
		for ( int i = 1; i <= 40; i++){
			departmentLists.add(docs.newEntity("연구소" + i, "길동"+i));
		}
		departmentRepository.saveAll(departmentLists);

    String url = "/api/departments/search";
    //Search - 부서명
    mvc.perform(post(url).content(docs::setKeyword, "연구소7")).andExpect(is2xx());
    //Search - 부서장
    mvc.perform(post(url).content(docs::setKeyword, "길동3")).andExpect(is2xx());
    //Search - 페이지네이션 10개씩 4페이지
    mvc.perform(post(url).content(docs::setKeyword, "").param("size", "10")).andExpect(is2xx());
    //Search - 정렬 deptName,desc;
    mvc.perform(post(url).content(docs::setKeyword, "").param("sort", "deptName,desc")).andExpect(is2xx());
  }
  
}

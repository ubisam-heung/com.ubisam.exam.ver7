package com.ubisam.exam.api.employees;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
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

import com.ubisam.exam.domain.Employee;

import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private EmployeeDocs docs;

	@Autowired
	private EmployeeRepository employeeRepository;

	// CRUD 테스트 - 이름
	@Test
	void contextLoads() throws Exception{
		// Crud - C
		mvc.perform(post("/api/employees")
		.content(docs::newEntity, "홍길동"))
		.andDo(print()).andExpect(is2xx())
		.andDo(result(docs::context, "entity1"));

		// Crud - R
		String url = docs.context("entity1", "$._links.self.href");
		mvc.perform(get(url)).andExpect(is2xx());

		//Crud - U
		Map<String, Object> body = docs.context("entity1", "$");
		mvc.perform(put(url).content(docs::updateEntity, body, "김길동"))
		.andExpect(is2xx());

		//Crud - D
		mvc.perform(delete(url)).andExpect(is2xx());
	}


	//Handler 테스트 - 이메일, 연봉
  @Test
  void contextLoads2() throws Exception{

		Specification<Employee> spec;
		List<Employee> result;
		boolean hasResult;

		//40명의 사원 추가
		List<Employee> userList = new ArrayList<>(); 
		for ( int i = 1; i <= 40; i++){
			userList.add(docs.newEntity("길동" + i, "abc"+i+"@abc.com", i+"000"));
		}
		employeeRepository.saveAll(userList);

		//1) 이메일을 통한 유저 찾기
		JpaSpecificationBuilder<Employee> query = JpaSpecificationBuilder.of(Employee.class);
		spec = query.where().and().eq("email", "abc4@abc.com").build();
		result = employeeRepository.findAll(spec);
    //쿼리 결과 행 1개를 찾고 그 행의 email필드가 "abc4@abc.com" 인지 검사
    hasResult = result.stream().anyMatch(u -> "abc4@abc.com".equals(u.getEmail()));
    assertEquals(true, hasResult);

		//2) 연봉을 통한 유저 찾기
		JpaSpecificationBuilder<Employee> query1 = JpaSpecificationBuilder.of(Employee.class);
		spec = query1.where().and().eq("salary", 3000).build();
		result = employeeRepository.findAll(spec);
    //쿼리 결과 행 1개를 찾고 그 행의 salary필드가 "3000" 인지 검사
    hasResult = result.stream().anyMatch(u -> 3000 == u.getSalary());
    assertEquals(true, hasResult);
	}

	//Search 테스트
	@Test
	void contextLoads3() throws Exception{

		//40명의 사원 추가
		List<Employee> userList = new ArrayList<>(); 
		for ( int i = 1; i <= 40; i++){
			userList.add(docs.newEntity("길동" + i, "abc"+i+"@abc.com", i+"000"));
		}
		employeeRepository.saveAll(userList);

		//Search - 검색
		mvc.perform(get("/api/employees/search/findByEmail").param("email", "abc2@abc.com")).andExpect(is2xx());
		mvc.perform(get("/api/employees/search/findBySalary").param("salary", "5000")).andExpect(is2xx());
		//Search - 페이지네이션 5개씩 8페이지
		mvc.perform(get("/api/employees").param("size", "5")).andExpect(is2xx());
		//Search - 정렬 salary, desc
		mvc.perform(get("/api/employees").param("sort", "salary,desc")).andExpect(is2xx());
	}


}

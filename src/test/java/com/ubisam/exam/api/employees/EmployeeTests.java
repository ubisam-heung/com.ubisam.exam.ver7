package com.ubisam.exam.api.employees;

import static io.u2ware.common.docs.MockMvcRestDocs.delete;
import static io.u2ware.common.docs.MockMvcRestDocs.get;
import static io.u2ware.common.docs.MockMvcRestDocs.is2xx;
import static io.u2ware.common.docs.MockMvcRestDocs.post;
import static io.u2ware.common.docs.MockMvcRestDocs.print;
import static io.u2ware.common.docs.MockMvcRestDocs.put;
import static io.u2ware.common.docs.MockMvcRestDocs.result;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private EmployeeDocs docs;

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

}

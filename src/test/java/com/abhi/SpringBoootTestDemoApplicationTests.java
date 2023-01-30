package com.abhi;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.abhi.entity.Student;
import com.abhi.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc // it is enable MockMvc for do work
@Testcontainers // this integrated testContainer
class SpringBoootTestDemoApplicationTests {

	// this mysql is of testContainer and only this container do work when docker
	// will running/open state
	@Container
	private MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest");;

	@Autowired
	private  StudentRepository studentRepository;

	@Autowired
	MockMvc mockMvc;

//given/when/then format ->BDD style

	@Test
	public void givenStudents_whenGetAllStudents_thenListOfStudents() throws Exception {
		System.out.println(mySQLContainer.getDatabaseName());
		System.out.println(mySQLContainer.getPassword());
		System.out.println(mySQLContainer.getUsername());
		System.out.println(mySQLContainer.getJdbcUrl());

		// Given - setup or precondition
		List<Student> students = List.of(
				Student.builder().firstName("Abhijit").lastName("Sagar").email("ab.sagar@gmail.com").build(),
				Student.builder().firstName("kishor").lastName("kumar").email("kishor@gmail.com").build());
		
		
		
		System.out.println("students: " + students.size());
		
		  this.studentRepository.saveAll(students);

		  
		// when - action
		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get("/api/students"));
		
		
		// Then - verify the output
		resultActions.andExpect(MockMvcResultMatchers.status().isOk());
		resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(students.size())));

	}

}

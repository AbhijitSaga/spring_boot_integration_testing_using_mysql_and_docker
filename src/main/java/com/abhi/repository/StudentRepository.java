package com.abhi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.entity.Student;

public interface StudentRepository extends JpaRepository<Student,Long> {

}

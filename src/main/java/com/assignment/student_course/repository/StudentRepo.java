package com.assignment.student_course.repository;

import com.assignment.student_course.beans.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer>{

	Optional<Student> findByEmailId(String emailId);

}
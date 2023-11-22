package com.assignment.student_course.repository;

import com.assignment.student_course.beans.Admin;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends ListCrudRepository<Admin, Integer> {

	Optional<Admin> findByEmailId(String emailId);
	
	boolean existsByEmailId(String emailId);

}

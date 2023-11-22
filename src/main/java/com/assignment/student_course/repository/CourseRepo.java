package com.assignment.student_course.repository;

import com.assignment.student_course.beans.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer>{

	public Course findByCourseName(String cName);

}

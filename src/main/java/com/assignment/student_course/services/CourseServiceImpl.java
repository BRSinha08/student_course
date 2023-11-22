package com.assignment.student_course.services;

import com.assignment.student_course.beans.Course;
import com.assignment.student_course.exceptions.ApplicationException;
import com.assignment.student_course.exceptions.CourseException;
import com.assignment.student_course.repository.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseRepo cRepo;

	
	@Override
	public Course registerNewCourse(Course course) throws CourseException {
		
		Course course1 = cRepo.findByCourseName(course.getCourseName());
		
		if(course1 == null)
		{
			cRepo.saveAndFlush(course);
			return course;
		}
		else throw new CourseException("course is already exist...");
	}

	@Override
	public void deleteCourse(Integer courseId) throws CourseException {
		
		Optional<Course> c = cRepo.findById(courseId);
		if(c.isPresent())
		{
			cRepo.deleteById(courseId);
		}

		else throw new CourseException("course doesnot exist with this Id "+courseId);
	}

	@Override
	public Course updateCourse(Course course) throws CourseException {
		
		Optional<Course> course1 = cRepo.findById(course.getCourseId());
		
		if(course1.isPresent())
			return cRepo.save(course);
		else
			throw new CourseException("course doesnot exist...");
	}

	@Override
	public List<Course> viewAllCourses() throws CourseException {

		List<Course> courses = cRepo.findAll();

		if(courses.size() == 0)
			throw new ApplicationException("2001","courses not found...",
					HttpStatus.INTERNAL_SERVER_ERROR);
		else
			return courses;
		
	}

}

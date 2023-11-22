package com.assignment.student_course.services;

import com.assignment.student_course.beans.Course;
import com.assignment.student_course.exceptions.CourseException;

import java.util.List;

public interface CourseService {

    public Course registerNewCourse(Course course) throws CourseException;

    public List<Course> viewAllCourses() throws CourseException;

    public Course updateCourse(Course course) throws CourseException;

    public void deleteCourse(Integer courseId) throws CourseException;

}

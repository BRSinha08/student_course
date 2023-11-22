package com.assignment.student_course.services;

import com.assignment.student_course.beans.Student;
import com.assignment.student_course.exceptions.CourseException;
import com.assignment.student_course.exceptions.StudentException;
import com.assignment.student_course.payload.request.StudentRegisterReq;

import java.util.List;

public interface StudentService {

    public String registerNewStudent(StudentRegisterReq student) throws StudentException;

    public List<Student> viewAllStudents() throws StudentException;

    public Student updateStudent(Student student) throws StudentException;

    public void deleteStudent(Integer roll) throws StudentException;

    public String addStudentToCourse(Integer courseId, Integer roll) throws CourseException, StudentException;

}

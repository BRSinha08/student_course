package com.assignment.student_course.controller;

import com.assignment.student_course.beans.Course;
import com.assignment.student_course.exceptions.CourseException;
import com.assignment.student_course.services.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {

    @Autowired
    private CourseService cService;

    @Operation(
            summary = "Register Course",
            description = "Register Course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation")
    })
    @PostMapping("/admin/register-course")
    public ResponseEntity<Course> registerNewCourse(@RequestBody Course course) throws CourseException {
        Course course1 = cService.registerNewCourse(course);

        return new ResponseEntity<>(course1, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Updated Course",
            description = "Updated Course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation")
    })
    @PutMapping("/admin/update-course")
    public ResponseEntity<Course> updateCourse(@RequestBody Course course) throws CourseException {
        Course course1 = cService.updateCourse(course);

        return new ResponseEntity<>(course1, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Delete Course",
            description = "Delete Course")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @DeleteMapping("/admin/delete-course/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable("courseId") Integer courseId) throws CourseException {
        cService.deleteCourse(courseId);

        return new ResponseEntity<>("Course deleted...", HttpStatus.OK);
    }

    @Operation(
            summary = "fetch All Courses",
            description = "fetch all courses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "successful operation")
    })
    @GetMapping("/all-courses")
    public ResponseEntity<List<Course>> viewAllCourses() throws CourseException {
        List<Course> courses = cService.viewAllCourses();

        return new ResponseEntity<>(courses, HttpStatus.FOUND);
    }
}

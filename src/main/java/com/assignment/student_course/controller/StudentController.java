package com.assignment.student_course.controller;

import com.assignment.student_course.beans.Student;
import com.assignment.student_course.exceptions.CourseException;
import com.assignment.student_course.exceptions.StudentException;
import com.assignment.student_course.payload.request.LoginRequest;
import com.assignment.student_course.payload.request.StudentRegisterReq;
import com.assignment.student_course.security.jwt.JWTUtils;
import com.assignment.student_course.security.services.UserDetailsServiceImpl;
import com.assignment.student_course.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
public class StudentController {

    @Autowired
    private StudentService sService;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Operation(
            summary = "Register Student",
            description = "Register Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation")
    })

    @PostMapping("/student/register-student")
    public ResponseEntity<String> registerNewStudent(@RequestBody StudentRegisterReq studentReq) throws StudentException {
        String message = sService.registerNewStudent(studentReq);

        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "loginn student",
            description = "Login Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation")
    })
    @PostMapping("/student/login-student")
    public ResponseEntity<?> loginStudent(@RequestBody LoginRequest loginDetails) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginDetails.getEmailId(), loginDetails.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginDetails.getEmailId());

        if (userDetails != null)
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));

        return new ResponseEntity<>("Invalid user log in details..!", HttpStatus.BAD_REQUEST);
    }

    @Operation(
            summary = "update student",
            description = "Update Student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation")
    })
    @PutMapping("/student/update-student")
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) throws StudentException {
        Student student1 = sService.updateStudent(student);

        return new ResponseEntity<>(student1, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Delete Student by rolll id",
            description = "Delete Student by roll id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation")
    })
    @DeleteMapping("/admin/delete-student/{roll}")
    public ResponseEntity<String> deleteStudent(@PathVariable("roll") Integer roll) throws StudentException {
        sService.deleteStudent(roll);

        return new ResponseEntity<>("Student deleted...", HttpStatus.OK);
    }

    @Operation(
            summary = "fetch All students",
            description = "fetch all students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "successful operation")
    })
    @GetMapping("/admin/all-students")
    public ResponseEntity<List<Student>> viewAllStudents() throws StudentException {
        List<Student> students = sService.viewAllStudents();

        return new ResponseEntity<>(students, HttpStatus.FOUND);
    }

    @Operation(
            summary = "Allocate student to course by course id and roll id",
            description = "Allocate student to course by coursed is and roll id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation")
    })
    @PostMapping("/admin/add-student-to-course/{courseId}/{roll}")
    public ResponseEntity<String> addStudentToCourse(@PathVariable("courseId") Integer courseId,
                                                     @PathVariable("roll") Integer roll)
            throws StudentException, CourseException {
        String message = sService.addStudentToCourse(courseId, roll);

        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Update Courses for one Student by course id and roll id",
            description = "Update Courses for one Student by course id and roll id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "successful operation")
    })
    @PostMapping("/admin/update-course-to-student/{courseId}/{roll}")
    public ResponseEntity<String> updateCourseToStudent(@PathVariable("courseId") Integer courseId,
                                                        @PathVariable("roll") Integer roll)
            throws StudentException, CourseException {
        String message = sService.updateCourseByStudent(courseId, roll);

        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }
}

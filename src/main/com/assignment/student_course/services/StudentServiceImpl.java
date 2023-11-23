package com.assignment.student_course.services;

import com.assignment.student_course.beans.Course;
import com.assignment.student_course.beans.Role;
import com.assignment.student_course.beans.Student;
import com.assignment.student_course.beans.User;
import com.assignment.student_course.beans.UserRole;
import com.assignment.student_course.exceptions.ApplicationException;
import com.assignment.student_course.exceptions.CourseException;
import com.assignment.student_course.exceptions.StudentException;
import com.assignment.student_course.payload.request.StudentDTO;
import com.assignment.student_course.payload.request.StudentRegisterReq;
import com.assignment.student_course.repository.CourseRepo;
import com.assignment.student_course.repository.StudentRepo;
import com.assignment.student_course.repository.UserRepo;
import com.assignment.student_course.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepo sRepo;

    @Autowired
    private CourseRepo cRepo;

    @Autowired
    private UserRepo uRepo;

    @Autowired
    private UserRoleRepo urRepo;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public String registerNewStudent(StudentRegisterReq studentReq) throws StudentException {

        Optional<Student> student0 = sRepo.findByEmailId(studentReq.getEmailId());

        if (student0.isEmpty()) {
            Set<String> strRoles = studentReq.getRoles();

            Set<UserRole> roles = new HashSet<>();

            if (strRoles == null) {
                UserRole studentRole = urRepo.findByUserRole(Role.STUDENT)
                        .orElseThrow(() -> new RuntimeException("Error: Student_Role is not found..!"));
                roles.add(studentRole);
            } else {

                strRoles.forEach(role -> {
                    switch (role.toLowerCase()) {
                        case "student":
                            UserRole studentRole = urRepo.findByUserRole(Role.STUDENT)
                                    .orElseThrow(() -> new RuntimeException("Error: Student_Role is not found..!"));
                            roles.add(studentRole);

                            break;
                        case "admin":
                            UserRole adminRole = urRepo.findByUserRole(Role.ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Admin_Role is not found..!"));
                            roles.add(adminRole);
                            break;
                    }
                });
            }

            User user = new User();
            user.setName(studentReq.getName());
            user.setEmailId(studentReq.getEmailId());
            user.setPassword(encoder.encode(studentReq.getPassword()));
            user.setRoles(roles);
            uRepo.save(user);

            Student student1 = new Student();
            student1.setName(studentReq.getName());
            student1.setAddress(studentReq.getAddress());
            student1.setEmailId(studentReq.getEmailId());
            student1.setPassword(encoder.encode(studentReq.getPassword()));
            student1.setRoles(roles);
            sRepo.save(student1);

            return studentReq.getName() + " you are registered successfully...";
        } else throw new ApplicationException("3001", "Student already exist with this emailId..!.",
                HttpStatus.IM_USED);
    }

    @Override
    public void deleteStudent(Integer studentId) throws StudentException {

        Optional<Student> ip = sRepo.findById(studentId);
        if (ip.isPresent()) {
            sRepo.deleteById(studentId);
        } else throw new StudentException("Student doesnot exist with this Id " + studentId);
    }

    @Override
    public Student updateStudent(StudentDTO student) throws StudentException {

        Optional<Student> student1 = sRepo.findById(student.getRoll());

        if (student1.isPresent()) {
            student1.get().setRoll(student.getRoll());
            student1.get().setName(student.getName());
            student1.get().setAddress(student.getAddress());
            student1.get().setPassword(student.getPassword());
            return sRepo.save(student1.get());
        }
        else
            throw new StudentException("student doesnot exist...");
    }

    @Override
    public List<Student> viewAllStudents() throws StudentException {

        List<Student> students = sRepo.findAll();

        if (students.size() == 0)
            throw new ApplicationException("3001","students not found...",HttpStatus.BAD_REQUEST);
        else
            return students;
    }

    @Override
    public String addStudentToCourse(Integer courseId, Integer roll) throws CourseException, StudentException {

        Optional<Student> student = sRepo.findById(roll);

        if (student.isPresent()) {
            Optional<Course> course = cRepo.findById(courseId);

            if (course.isPresent()) {
                Student s1 = student.get();
                Course c1 = course.get();

                List<Student> students = c1.getStudents();
                students.add(s1);

                c1.setStudents(students);

                cRepo.save(c1);

                return s1.getName() + " student added to the course " + c1.getCourseName();
            } else throw new CourseException("Course not found...");
        } else throw new StudentException("Student not found...");
    }


}

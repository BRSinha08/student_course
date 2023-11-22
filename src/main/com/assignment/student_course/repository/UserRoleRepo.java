package com.assignment.student_course.repository;

import com.assignment.student_course.beans.Role;
import com.assignment.student_course.beans.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByUserRole(Role userRole);

}

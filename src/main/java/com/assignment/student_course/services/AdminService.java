package com.assignment.student_course.services;

import com.assignment.student_course.exceptions.UserException;
import com.assignment.student_course.payload.request.AdminRegisterReq;

public interface AdminService {

    public String registerNewAdmin(AdminRegisterReq admin) throws UserException;

    public String getCurrentLoggedInUser();
}

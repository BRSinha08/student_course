package com.assignment.student_course.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegisterReq {

    private String name;
    private String address;
    private String emailId;
    private String password;

    private Set<String> roles;
}

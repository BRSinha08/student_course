package com.assignment.student_course.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminRegisterReq {

    private String name;
    private String emailId;
    private String password;

    private Set<String> roles;

}

package com.assignment.student_course.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roll;
	private String name;
	private String address;
	private String emailId;
	private String password;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "Student_Roles",
	joinColumns = @JoinColumn(name = "Student_Id"),
	inverseJoinColumns = @JoinColumn(name = "Role_Id"))
	private Set<UserRole> roles = new HashSet<>();
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "students")
	private List<Course> courses = new ArrayList<>();
	
	
	
	
}

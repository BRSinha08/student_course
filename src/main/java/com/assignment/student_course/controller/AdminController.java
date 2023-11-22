package com.assignment.student_course.controller;


import com.assignment.student_course.exceptions.UserException;
import com.assignment.student_course.payload.request.AdminRegisterReq;
import com.assignment.student_course.payload.request.LoginRequest;
import com.assignment.student_course.payload.response.JwtResponse;
import com.assignment.student_course.security.jwt.JWTUtils;
import com.assignment.student_course.security.services.UserDetailsServiceImpl;
import com.assignment.student_course.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController {
	
	@Autowired
	private AdminService aService;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@PostMapping("/register-admin")
	public ResponseEntity<String> registerNewAdmin(@RequestBody AdminRegisterReq admin) throws UserException
	{

		String message = aService.registerNewAdmin(admin);
		
		return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/admin/login-admin")
	public ResponseEntity<?> adminLogIn(@RequestBody LoginRequest loginDetails)
	{
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDetails.getEmailId(), loginDetails.getPassword()));
		
		UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(loginDetails.getEmailId());
		
		if(userDetails != null)
			return ResponseEntity.ok(new JwtResponse(jwtUtils.generateToken(userDetails)));
		
		return new ResponseEntity<>("Invalid admin log in details..!", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/user/get-current-user")
	public ResponseEntity<String> getCurrentLoggedInUser()
	{
		String name = aService.getCurrentLoggedInUser();
		
		return new ResponseEntity<String>(name, HttpStatus.FOUND);
	}
}

package com.assignment.student_course.services;

import com.assignment.student_course.beans.Admin;
import com.assignment.student_course.beans.Role;
import com.assignment.student_course.beans.User;
import com.assignment.student_course.beans.UserRole;
import com.assignment.student_course.exceptions.ApplicationException;
import com.assignment.student_course.exceptions.UserException;
import com.assignment.student_course.payload.request.AdminRegisterReq;
import com.assignment.student_course.repository.AdminRepo;
import com.assignment.student_course.repository.UserRepo;
import com.assignment.student_course.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepo aRepo;

	@Autowired
	private UserRepo uRepo;

	@Autowired
	private UserRoleRepo urRepo;

	@Autowired
	PasswordEncoder encoder;

	@Override
	public String registerNewAdmin(AdminRegisterReq adminReq) throws UserException {

		Optional<Admin> admin0 = aRepo.findByEmailId(adminReq.getEmailId());

		if (admin0.isEmpty()) {
			
			Set<String> strRoles = adminReq.getRoles();

			Set<UserRole> roles = new HashSet<>();

			if (strRoles == null) {
				UserRole adminRole = urRepo.findByUserRole(Role.ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Admin Role is not found..!"));
				roles.add(adminRole);
			} else {

				strRoles.forEach(role -> {
					switch (role) {
					case "admin":
						UserRole adminRole = urRepo.findByUserRole(Role.ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Admin Role is not found."));
						roles.add(adminRole);

						break;
					case "student":
						UserRole studentRole = urRepo.findByUserRole(Role.STUDENT)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(studentRole);
						break;
					}
				});
			}
			
			User user = new User();
			user.setName(adminReq.getName());
			user.setEmailId(adminReq.getEmailId());
			user.setPassword(encoder.encode(adminReq.getPassword()));
			user.setRoles(roles);
			uRepo.save(user);
			
			Admin admin1 = new Admin();
			admin1.setName(adminReq.getName());
			admin1.setEmailId(adminReq.getEmailId());
			admin1.setPassword(encoder.encode(adminReq.getPassword()));
			admin1.setRoles(roles);
			aRepo.save(admin1);

			return adminReq.getName() + " you are registered successfully...";
			
		}
		  else {
			throw new ApplicationException("1001","User already exist with this emailId..!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public String getCurrentLoggedInUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String userName = authentication.getName();
		
		Optional<User> user = uRepo.findByEmailId(userName);
		
		return user.get().getName();
		
	}

}

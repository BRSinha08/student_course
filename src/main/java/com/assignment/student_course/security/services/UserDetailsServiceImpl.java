package com.assignment.student_course.security.services;

import com.assignment.student_course.beans.User;
import com.assignment.student_course.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepo uRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = uRepo.findByEmailId(username).orElseThrow(
				() -> new UsernameNotFoundException("User not found..!"));
			
		return new UserDetailsImpl(user);
		
	}

}

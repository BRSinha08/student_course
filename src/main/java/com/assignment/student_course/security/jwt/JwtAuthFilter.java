package com.assignment.student_course.security.jwt;

import com.assignment.student_course.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		final String authHeader = request.getHeader("Authorization");
		String userEmail;
		String jwtToken;
		
		if(authHeader == null || !authHeader.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}

		jwtToken = authHeader.substring(7);
		userEmail = jwtUtils.extractUsername(jwtToken);
		
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null)
		{
			UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userEmail);
			
			final Boolean isTokenValid = jwtUtils.validateToken(jwtToken, userDetails);
			
			if(isTokenValid)
			{
				UsernamePasswordAuthenticationToken authToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		else
			throw new RuntimeException("Invalid username/token ..!");
		
		
		filterChain.doFilter(request, response);
		
	}


}

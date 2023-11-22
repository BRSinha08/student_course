package com.assignment.student_course.security.services;

import com.assignment.student_course.security.jwt.AuthJwtEntryPoint;
import com.assignment.student_course.security.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringWebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthJwtEntryPoint authJwtEntryPoint;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    /*auth.requestMatchers("/api/admin/delete-student/{roll}", "/api/admin/all-students",
                                    "/api/admin/register-course", "/api/admin/update-course",
                                    "/api/admin/delete-course/{courseId}",
                                    "/api/admin/delete-student/{roll}", "/api/admin/all-students","/swagger-ui/index.html#/")
                            .hasAuthority("ADMIN").requestMatchers("/api/student/update-student").hasAuthority("STUDENT").
                            requestMatchers("/api/register-admin", "/api/admin/login-admin", "/api/student/register-student",
                                    "/api/student/login-student", "/api/all-courses",
                                    "/api/user/get-current-user","/swagger-ui/index.html#/").permitAll()
                            .anyRequest().fullyAuthenticated();*/
                   // auth.anyRequest().fullyAuthenticated();
                    //auth.requestMatchers("/swagger-ui/index.html#/").hasAnyAuthority().anyRequest().permitAll();
                   // auth.anyRequest().fullyAuthenticated();
                    auth.anyRequest().permitAll();


                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}

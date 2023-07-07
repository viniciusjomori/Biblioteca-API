package com.br.Library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    @Autowired
    private RoleFilter roleFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(roleFilter, UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "book/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "book/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.DELETE, "book/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "loan/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "loan/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.PUT, "loan/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "reserve/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "user").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "client/**").hasRole("CLIENT")
                .requestMatchers(HttpMethod.POST, "client/reserve/**").hasRole("CLIENT")
                .requestMatchers(HttpMethod.PUT, "client/reserve/**").hasRole("CLIENT")
                .requestMatchers(HttpMethod.GET, "role/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.GET, "employee/**").hasRole("EMPLOYEE")
                .requestMatchers(HttpMethod.POST, "employee/**").hasRole("ADMINISTRATOR")
                .requestMatchers(HttpMethod.PUT, "employee/**").hasRole("ADMINISTRATOR")
                .requestMatchers(HttpMethod.DELETE, "employee/**").hasRole("ADMINISTRATOR")
                
                .anyRequest().permitAll()
                .and().cors();
                
        http.headers().frameOptions().disable();

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = """
                ROLE_ADMINISTRATOR > ROLE_EMPLOYEE
                """; 
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

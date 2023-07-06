package com.br.Library.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.Library.dto.EmployeeRequestDTO;
import com.br.Library.enums.RoleName;
import com.br.Library.exceptions.ResponseStatusException;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;
import com.br.Library.repository.RoleRepository;
import com.br.Library.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleHierarchy roleHierarchy;

    public Iterable<UserModel> getAll() {
        Optional<RoleModel> optional = roleRepository.findByName(RoleName.ROLE_EMPLOYEE);
        return optional.get().getUsers();
    }

    @Transactional
    public UserModel createEmployee(EmployeeRequestDTO dto) {
        if(!isEmployee(dto.role())) {
            throw new ResponseStatusException(
                "invalid role", 
                HttpStatus.FORBIDDEN
            );
        }
        UserModel employee = new UserModel();
        employee.setUsername(dto.username());
        employee.setRole(
            roleRepository.findByName(RoleName.valueOf(dto.role())).get()
        );
        employee.setPassword(passwordEncoder.encode(dto.password()));
        return userRepository.save(employee);
    }

    @Transactional
    public UserModel updateEmployee(EmployeeRequestDTO dto, long id) {
        if(!isEmployee(dto.role())) {
            throw new ResponseStatusException(
                "invalid role", 
                HttpStatus.FORBIDDEN
            );
        }
        UserModel employee = findById(id);
        employee.setUsername(dto.username());
        employee.setRole(
            roleRepository.findByName(RoleName.valueOf(dto.role())).get()
        );
        employee.setPassword(passwordEncoder.encode(dto.password()));
        return userRepository.save(employee);
    }

    public void deleteById(long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(
                "employee not found", 
                HttpStatus.NOT_FOUND
            );
        }
    }

    public UserModel findById(long id) {
        Optional<UserModel> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(
                "employee not found", 
                HttpStatus.NOT_FOUND
            );
        }
    }

    private boolean isEmployee(String role) {
        return roleHierarchy.getReachableGrantedAuthorities(
            Collections.singleton(new SimpleGrantedAuthority(role)))
            .contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"));
    }
}

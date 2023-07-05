package com.br.Library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.Library.dto.UserRequestDTO;
import com.br.Library.enums.RoleName;
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

    @Transactional
    public UserModel createEmployee(UserRequestDTO dto, String role) {
        RoleName roleName = RoleName.valueOf(role);
        return createEmployee(dto, roleName);
    }

    @Transactional
    public UserModel createEmployee(UserRequestDTO dto, RoleName roleName) {
        UserModel employee = new UserModel();
        employee.setUsername(dto.username());
        employee.setRole(roleRepository.findByName(roleName).get());
        employee.setPassword(passwordEncoder.encode(dto.password()));
        return userRepository.save(employee);
    }
}

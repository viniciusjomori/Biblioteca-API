package com.br.Library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.dto.EmployeeRequestDTO;
import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;
import com.br.Library.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public Iterable<UserModel> getAll() {
        Iterable<RoleModel> roles = roleService.findByNameOrAbove(RoleName.ROLE_EMPLOYEE);
        Collection<UserModel> employees = new ArrayList<>();
        for(RoleModel role : roles) {
            employees.addAll(role.getUsers());
        }
        return employees;
    }

    @Transactional
    public UserModel createEmployee(EmployeeRequestDTO dto) {
        if(isEmployee(dto.role())) {
            UserModel employee = new UserModel();
            employee.setUsername(dto.username());
            employee.setRole(
                roleService.findByName(dto.role())
            );
            employee.setPassword(passwordEncoder.encode(dto.password()));
            return userRepository.save(employee);
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "The role must be a employee"
            );
        }
        
    }

    @Transactional
    public UserModel updateEmployee(EmployeeRequestDTO dto, long id) {
        if(isEmployee(dto.role())) {
            UserModel employee = findById(id);
            employee.setUsername(dto.username());
            employee.setRole(
                roleService.findByName(dto.role())
            );
            employee.setPassword(passwordEncoder.encode(dto.password()));
            return userRepository.save(employee);
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "The role must be a employee"
            );
        }
    }

    public void deleteById(long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Employee not found"
            );
        }
    }

    public UserModel findById(long id) {
        Optional<UserModel> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Employee not found"
            );
        }
    }

    private boolean isEmployee(String role) {
        return roleService.contains(
            roleService.toRoleName(role),
            RoleName.ROLE_EMPLOYEE
        );
    }
}

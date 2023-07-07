package com.br.Library.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.dto.EmployeeRequestDTO;
import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {
    
    @Autowired
    private UserService userService;

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
        if(isEmployee(dto.getRole())) {
            UserModel employee = new UserModel();
            employee.setUsername(dto.getUsername());
            employee.setRole(
                roleService.findByName(dto.getRole())
            );
            employee.setPassword(dto.getPassword());
            return userService.createUser(employee);
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "The role must be a employee"
            );
        }
    }

    @Transactional
    public UserModel updateEmployee(EmployeeRequestDTO dto, long id) {
        if(isEmployee(dto.getRole())) {
            UserModel employee = new UserModel();
            employee.setUsername(dto.getUsername());
            employee.setRole(
                roleService.findByName(dto.getRole())
            );
            employee.setPassword(dto.getPassword());
            return userService.updateUser(employee, id);
        } else {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "The role must be a employee"
            );
        }
    }

    public void deleteById(long id) {
        UserModel user = findById(id);
        if(isEmployee(user)) userService.deleteById(id);
        else {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "The user is not an employee"
            );
        }
    }

    public UserModel findById(long id) {
        UserModel user = userService.findById(id);
        if (isEmployee(user)) return user;
        else {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "The user is not an employee"
            );
        }
    }

    private boolean isEmployee(UserModel user) {
        return isEmployee(user.getRole().getName());
    }

    private boolean isEmployee(RoleName roleName) {
        return roleService.contains(
            roleName,
            RoleName.ROLE_EMPLOYEE
        );
    }
}

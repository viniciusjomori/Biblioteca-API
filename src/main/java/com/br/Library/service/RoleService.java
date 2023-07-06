package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.Library.enums.RoleName;
import com.br.Library.exceptions.ResponseStatusException;
import com.br.Library.model.RoleModel;
import com.br.Library.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository repository;

    public Iterable<RoleModel> getAll() {
        return repository.findAll();
    }

    public RoleModel findByName(String name) {
        name = name.toUpperCase();
        RoleName roleName = RoleName.valueOf(name);
        Optional<RoleModel> optional = repository.findByName(roleName);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(
                "Role not found", 
                HttpStatus.NOT_FOUND
            );
        }
    }
}

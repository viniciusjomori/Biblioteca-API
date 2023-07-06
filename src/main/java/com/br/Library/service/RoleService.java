package com.br.Library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.enums.RoleName;
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
        if(!name.startsWith("ROLE_")) {
            name = "ROLE_"+name;
        }
        try {
            RoleName roleName = RoleName.valueOf(name);
            return repository.findByName(roleName).get();
        } catch(IllegalArgumentException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Role not found"
            );
        }
    }
}

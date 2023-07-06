package com.br.Library.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.repository.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleHierarchy roleHierarchy;

    public RoleName toRoleName(String string) {
        string = string.toUpperCase();
        if(!string.startsWith("ROLE_")) {
            string = "ROLE_"+string;
        }
        try {
            return RoleName.valueOf(string);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Role not found"
            );
        }
    }

    public Iterable<RoleModel> getAll() {
        return repository.findAll();
    }

    public RoleModel findByName(String string) {
        RoleName roleName = toRoleName(string);
        return repository.findByName(roleName).get();
    }

    public Iterable<RoleModel> findByNameOrAbove(String string) {
        RoleName roleName = toRoleName(string);
        Collection<RoleModel> allRoles = repository.findAll();
        Collection<RoleModel> findedRoles = new ArrayList<>();
        for(RoleModel role : allRoles) {
            if(contains(role.getName(), roleName)) {
                findedRoles.add(role);
            }
        }
        return findedRoles;
    }

    public boolean contains(RoleName thiz, RoleName that) {
        return roleHierarchy.getReachableGrantedAuthorities(
            Collections.singleton(new SimpleGrantedAuthority(thiz.toString())))
            .contains(new SimpleGrantedAuthority(that.toString()));
    }

    public Iterable<RoleModel> findByNameOrBelow(String string) {
        RoleName roleName = toRoleName(string);
        return findByNameOrBelow(roleName);
    }

    public Iterable<RoleModel> findByNameOrBelow(RoleName role) {
        Collection<? extends GrantedAuthority> authorities = roleHierarchy.getReachableGrantedAuthorities(
            Collections.singleton(new SimpleGrantedAuthority(role.toString())));
        Collection<RoleName> roleNames = new ArrayList<>();
        for(GrantedAuthority authority : authorities) {
            roleNames.add(
                toRoleName(authority.getAuthority())
            );
        }
        return repository.findByNameIn(roleNames);
    }

    

}

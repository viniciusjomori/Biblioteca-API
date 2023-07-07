package com.br.Library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.dto.UserRequestDTO;
import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;

import jakarta.transaction.Transactional;

@Service
public class ClientService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public Iterable<UserModel> getAll() {
        RoleModel clientRoleModel = roleService.findByName(RoleName.ROLE_CLIENT);
        return clientRoleModel.getUsers(); 
    }

    @Transactional
    public UserModel createClient(UserRequestDTO dto) {
        UserModel client = new UserModel();
        client.setUsername(dto.username());
        client.setPassword(dto.password());
        client.setRole(
            roleService.findByName(RoleName.ROLE_CLIENT)
        );
        return userService.createUser(client);
    }

    public UserModel findById(long id) {
        Iterable<UserModel> clients = getAll();
        for(UserModel client : clients) {
            if(client.getId() == id) {
                return client;
            }
        }

        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Client not found"
        );
    }

    public UserModel getAuthenticatedClient() {
        UserModel user = userService.getAuthenticatedUser();
        if(isClient(user)) return user;
        else {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "The user is not an client"
            );
        }
    }

    public void deleteAuthenticatedClient(){
        UserModel user = getAuthenticatedClient();
        userService.deleteById(user.getId());
    } 

    public boolean isClient(UserModel user) {
        return user.getRole().getName() == RoleName.ROLE_CLIENT;
    }
}

package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.dto.UserRequestDTO;
import com.br.Library.enums.RoleName;
import com.br.Library.model.RoleModel;
import com.br.Library.model.UserModel;
import com.br.Library.repository.RoleRepository;
import com.br.Library.repository.UserRepository;
import com.br.Library.security.TokenUtil;

import jakarta.transaction.Transactional;

@Service
public class ClientService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<UserModel> getAll() {
        Optional<RoleModel> clientRoleModel = roleRepository.findByName(RoleName.ROLE_CLIENT);
        return clientRoleModel.get().getUsers(); 
    }

    @Transactional
    public UserModel createClient(UserRequestDTO dto) {
        UserModel client = new UserModel();
        client.setUsername(dto.username());
        client.setPassword(passwordEncoder.encode(dto.password()));
        client.setRole(roleRepository.findByName(RoleName.ROLE_CLIENT).get());
        return userRepository.save(client);
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

    public UserModel findOnlineClient(String tokenJwt) {
        String username = TokenUtil.getSubject(tokenJwt);
        Optional<RoleModel> opt = roleRepository.findByName(RoleName.ROLE_CLIENT);
        RoleModel role = opt.get();
        for (UserModel user : role.getUsers()) {
            if(user.getUsername().equals(username)) return user;
        }
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            "Client not found"
        );
    }
}

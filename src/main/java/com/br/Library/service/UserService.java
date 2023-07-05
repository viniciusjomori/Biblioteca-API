package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.Library.exceptions.user.UserNotFoundException;
import com.br.Library.model.UserModel;
import com.br.Library.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;


    public Iterable<UserModel> getAll() {
        return repository.findAll();
    }

    public UserModel findById(Long id) {
        Optional<UserModel> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException(null);
        }
    }

    public UserModel findByUsername(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        return (UserModel) userDetails;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> optional = repository.findByUsername(username);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException(null);
        }
    }
    
}

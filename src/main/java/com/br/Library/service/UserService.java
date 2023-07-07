package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.model.UserModel;
import com.br.Library.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Iterable<UserModel> getAll() {
        return repository.findAll();
    }

    public UserModel createUser(UserModel user) {
        user.setId(Long.valueOf(0));
        return repository.save(user);
    }

    public UserModel updateUser(UserModel newUser, long id) {
        UserModel user = findById(id);
        BeanUtils.copyProperties(newUser, user);
        user.setId(id);
        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );
        return repository.save(user);
    }

    public UserModel findById(Long id) {
        Optional<UserModel> optional = repository.findById(id);
        if(optional.isPresent()) return optional.get();
        else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            );
        }
    }

    public UserModel findByUsername(String username) {
        UserDetails userDetails = loadUserByUsername(username);
        if(userDetails != null) return (UserModel) userDetails;
        else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            );
        }
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public UserModel getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserModel) return (UserModel) principal;
        else {
            throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED,
                "The user is not authenticated"
            );
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> user =  repository.findByUsername(username);
        if(user.isPresent()) {
            return user.get();
        } else {
            return null;
        }
    }
    
}

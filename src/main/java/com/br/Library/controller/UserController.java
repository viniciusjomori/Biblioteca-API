package com.br.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.Library.dto.UserRequestDTO;
import com.br.Library.dto.UserResponseDTO;
import com.br.Library.mapper.UserMapper;
import com.br.Library.model.UserModel;
import com.br.Library.security.TokenUtil;
import com.br.Library.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid UserRequestDTO login) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(login.username(), login.password());

        authManager.authenticate(usernamePasswordAuthenticationToken);

        return new ResponseEntity<String>(
            TokenUtil.encodeToken(login.username()),
            HttpStatus.OK
        );
    }

    @GetMapping("/online")
    public ResponseEntity<UserResponseDTO> getOnlineUser(@RequestHeader("Authorization") String tokenJwt) {
        String username = TokenUtil.getSubject(tokenJwt);
        UserModel user = service.findByUsername(username);
        return ResponseEntity.ok(mapper.toResponseDTO(user));
    }
}

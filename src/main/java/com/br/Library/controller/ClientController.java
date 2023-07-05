package com.br.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.Library.dto.UserRequestDTO;
import com.br.Library.dto.UserResponseDTO;
import com.br.Library.mapper.UserMapper;
import com.br.Library.model.UserModel;
import com.br.Library.service.ClientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController {
    
    @Autowired
    private ClientService service;

    @Autowired
    private UserMapper mapper;

    @PostMapping
    public ResponseEntity<UserResponseDTO> createClient(@RequestBody @Valid UserRequestDTO requestDTO) {
        UserModel client = service.createClient(requestDTO);
        return ResponseEntity.ok(mapper.toResponseDTO(client));
    }

    @GetMapping
    public ResponseEntity<Iterable<UserResponseDTO>> getAll() {
        Iterable<UserModel> models = service.getAll();
        return ResponseEntity.ok(mapper.toListResponseDTO(models));
    }

}

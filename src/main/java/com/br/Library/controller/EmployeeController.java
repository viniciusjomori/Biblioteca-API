package com.br.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.Library.dto.EmployeeRequestDTO;
import com.br.Library.dto.ResponseMessage;
import com.br.Library.dto.UserResponseDTO;
import com.br.Library.mapper.UserMapper;
import com.br.Library.model.UserModel;
import com.br.Library.service.EmployeeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "*")
public class EmployeeController {
    
    @Autowired
    private EmployeeService service;

    @Autowired
    private UserMapper mapper;

    @Autowired
    private ResponseMessage responseMessage;

    @GetMapping
    public ResponseEntity<Iterable<UserResponseDTO>> getAll() {
        Iterable<UserModel> users = service.getAll();
        return ResponseEntity.ok(mapper.toListResponseDTO(users));
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createEmployee(@RequestBody @Valid EmployeeRequestDTO dto) {
        UserModel model = service.createEmployee(dto);
        return ResponseEntity.ok(mapper.toResponseDTO(model));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponseDTO> createEmployee(@RequestBody @Valid EmployeeRequestDTO dto, @PathVariable Long id) {
        UserModel model = service.updateEmployee(dto, id);
        return ResponseEntity.ok(mapper.toResponseDTO(model));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMessage> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        responseMessage.setHttpStatus(HttpStatus.OK);
        responseMessage.setMessage("Deleted Succesfully");
        return ResponseEntity.ok(responseMessage);
    }

}
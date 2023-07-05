package com.br.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.Library.dto.RoleResponseDTO;
import com.br.Library.mapper.RoleMapper;
import com.br.Library.model.RoleModel;
import com.br.Library.service.RoleService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/role")
public class RoleController {
    
    @Autowired
    private RoleService service;

    @Autowired
    private RoleMapper mapper;

    @GetMapping
    public ResponseEntity<Iterable<RoleResponseDTO>> getAll() {
        Iterable<RoleModel> models = service.getAll();
        Iterable<RoleResponseDTO> dtos = mapper.toListResponseDTO(models);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("{name}")
    public ResponseEntity<RoleResponseDTO> getByName(@PathVariable String name) {
        RoleModel model = service.findByName(name);
        RoleResponseDTO dto = mapper.toResponseDTO(model);
        return ResponseEntity.ok(dto);
    }
    
}

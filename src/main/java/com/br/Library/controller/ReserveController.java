package com.br.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.Library.dto.ReserveResponseDTO;
import com.br.Library.mapper.ReserveMapper;
import com.br.Library.model.ReserveModel;
import com.br.Library.service.ReserveService;


@RestController
@RequestMapping("/reserve")
@CrossOrigin(origins = "*")
public class ReserveController {
    
    @Autowired
    private ReserveService service;

    @Autowired
    private ReserveMapper mapper;

    @GetMapping
    public ResponseEntity<Iterable<ReserveResponseDTO>> getAll() {
        Iterable<ReserveModel> reserves = service.getAll();
        return ResponseEntity.ok(mapper.toListResponseDTO(reserves));
    }

    @GetMapping("book/{bookId}")
    public ResponseEntity<Iterable<ReserveResponseDTO>> getReservesByBook(@PathVariable long bookId) {
        Iterable<ReserveModel> reserves = service.findAllByBook(bookId);
        return ResponseEntity.ok(mapper.toListResponseDTO(reserves));
    }

    @GetMapping("client/{clientId}")
    public ResponseEntity<Iterable<ReserveResponseDTO>> getReservesByClient(@PathVariable long clientId) {
        Iterable<ReserveModel> reserves = service.findAllByClient(clientId);
        return ResponseEntity.ok(mapper.toListResponseDTO(reserves));
    }

    @GetMapping("status/{status}")
    public ResponseEntity<Iterable<ReserveResponseDTO>> getAllByStatus(@PathVariable String status) {
        Iterable<ReserveModel> reserves = service.findAllByStatus(status);
        return ResponseEntity.ok(mapper.toListResponseDTO(reserves));
    }

}

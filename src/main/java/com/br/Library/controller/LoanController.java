package com.br.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.Library.dto.LoanRequestDTO;
import com.br.Library.dto.LoanResponseDTO;
import com.br.Library.mapper.LoanMapper;
import com.br.Library.model.LoanModel;
import com.br.Library.service.LoanService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*")
public class LoanController {
    
    @Autowired
    private LoanService service;

    @Autowired
    private LoanMapper mapper;

    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@RequestBody @Valid LoanRequestDTO dto) {
        LoanModel model = service.createLoan(dto);
        return ResponseEntity.ok(mapper.toResponseDTO(model));
    }

    @PutMapping("/deliver/{id}")
    public ResponseEntity<LoanResponseDTO> deliver(@PathVariable long id) {
        LoanModel loan = service.deliver(id);
        return ResponseEntity.ok(mapper.toResponseDTO(loan));
    }

    @GetMapping
    public ResponseEntity<Iterable<LoanResponseDTO>> getAll() {
        Iterable<LoanModel> loans = service.getAll();
        return ResponseEntity.ok(mapper.toListResponseDTO(loans));
    }

    @GetMapping("book/{bookId}")
    public ResponseEntity<Iterable<LoanResponseDTO>> getLoansByBook(@PathVariable long bookId) {
        Iterable<LoanModel> loans = service.findAllByBook(bookId);
        return ResponseEntity.ok(mapper.toListResponseDTO(loans));
    }

    @GetMapping("client/{clientId}")
    public ResponseEntity<Iterable<LoanResponseDTO>> getLoansByClient(@PathVariable long clientId) {
        Iterable<LoanModel> loans = service.findAllByClient(clientId);
        return ResponseEntity.ok(mapper.toListResponseDTO(loans));
    }
}

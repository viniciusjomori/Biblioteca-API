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
import com.br.Library.service.ReserveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*")
public class LoanController {
    
    @Autowired
    private LoanService loanService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private LoanMapper mapper;

    @PostMapping
    public ResponseEntity<LoanResponseDTO> createLoan(@RequestBody @Valid LoanRequestDTO dto) {
        LoanModel model = loanService.createLoan(dto);
        return ResponseEntity.ok(mapper.toResponseDTO(model));
    }

    @PutMapping("/deliver/{id}")
    public ResponseEntity<LoanResponseDTO> deliver(@PathVariable long id) {
        LoanModel loan = loanService.deliver(id);
        return ResponseEntity.ok(mapper.toResponseDTO(loan));
    }

    @GetMapping
    public ResponseEntity<Iterable<LoanResponseDTO>> getAll() {
        Iterable<LoanModel> loans = loanService.getAll();
        return ResponseEntity.ok(mapper.toListResponseDTO(loans));
    }

    @GetMapping("book/{bookId}")
    public ResponseEntity<Iterable<LoanResponseDTO>> getLoansByBook(@PathVariable long bookId) {
        Iterable<LoanModel> loans = loanService.findAllByBook(bookId);
        return ResponseEntity.ok(mapper.toListResponseDTO(loans));
    }

    @GetMapping("client/{clientId}")
    public ResponseEntity<Iterable<LoanResponseDTO>> getLoansByClient(@PathVariable long clientId) {
        Iterable<LoanModel> loans = loanService.findAllByClient(clientId);
        return ResponseEntity.ok(mapper.toListResponseDTO(loans));
    }

    @PostMapping("from-reserve/{reserveId}")
    public ResponseEntity<LoanResponseDTO> fromReserve(@PathVariable long reserveId) {
        LoanModel loan = reserveService.createLoanFromReserve(reserveId);
        return ResponseEntity.ok(mapper.toResponseDTO(loan));
    }
}

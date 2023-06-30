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

import com.br.Library.dto.BookRequestDTO;
import com.br.Library.dto.ResponseMessage;
import com.br.Library.model.BookModel;
import com.br.Library.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/book")
@CrossOrigin(origins = "*")
public class BookController {
    
    @Autowired
    private BookService service;

    @Autowired
    private ResponseMessage responseMessage;

    @PostMapping
    public ResponseEntity<BookModel> createBook(@RequestBody @Valid BookRequestDTO dto) {
        return ResponseEntity.ok(service.createBook(dto));
    }

    @GetMapping
    public ResponseEntity<Iterable<BookModel>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookModel> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookModel> updateBook(@RequestBody @Valid BookRequestDTO dto, @PathVariable Long id) {
        return ResponseEntity.ok(service.updateBook(dto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        responseMessage.setMessage("Deleted succesfully");
        responseMessage.setHttpStatus(HttpStatus.OK);
        return ResponseEntity.ok(responseMessage);
    }
}


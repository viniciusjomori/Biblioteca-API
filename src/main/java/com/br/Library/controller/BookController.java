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
import com.br.Library.dto.BookResponseDTO;
import com.br.Library.dto.ResponseMessage;
import com.br.Library.mapper.BookMapper;
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
    private BookMapper mapper;

    @Autowired
    private ResponseMessage responseMessage;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody @Valid BookRequestDTO dto) {
        BookModel book = service.createBook(dto);
        return ResponseEntity.ok(mapper.toResponseDTO(book));
    }

    @GetMapping
    public ResponseEntity<Iterable<BookResponseDTO>> getAll() {
        Iterable<BookModel> books = service.getAll();
        return ResponseEntity.ok(mapper.toListResponseDTO(books));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> findById(@PathVariable Long id) {
        BookModel book = service.findById(id);
        return ResponseEntity.ok(mapper.toResponseDTO(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody @Valid BookRequestDTO dto, @PathVariable Long id) {
        BookModel book = service.updateBook(dto, id);
        return ResponseEntity.ok(mapper.toResponseDTO(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        responseMessage.setMessage("Deleted succesfully");
        responseMessage.setHttpStatus(HttpStatus.OK);
        return ResponseEntity.ok(responseMessage);
    }

}


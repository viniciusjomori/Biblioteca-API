package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.dto.BookRequestDTO;
import com.br.Library.mapper.BookMapper;
import com.br.Library.model.BookModel;
import com.br.Library.repository.BookRepository;


@Service
public class BookService {
    
    @Autowired
    private BookRepository repository;

    @Autowired
    private BookMapper mapper;

    public BookModel createBook(BookRequestDTO dto) {
        BookModel model = mapper.toEntity(dto);
        model.setAvailableCopies(dto.totalCopies());
        return repository.save(model);
    }

    public Iterable<BookModel> getAll() {
        return repository.findAll();
    }

    public BookModel findById(Long id) {
        Optional<BookModel> optional = repository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Book not found"
            );
        }
    }

    public boolean existsById(Long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            return true;
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Book not found"
            );
        }
    }

    public BookModel updateBook(BookRequestDTO dto, Long id) {
        BookModel model = findById(id);
        BeanUtils.copyProperties(dto, model);
        return repository.save(model);
    }

    public void deleteById(Long id) {
        if(existsById(id)) {
            repository.deleteById(id);
        }
    }
}

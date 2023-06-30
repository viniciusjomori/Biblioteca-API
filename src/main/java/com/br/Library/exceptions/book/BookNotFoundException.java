package com.br.Library.exceptions.book;

import com.br.Library.exceptions.abstracts.ResourceNotFoundException;

public class BookNotFoundException extends ResourceNotFoundException {
    public BookNotFoundException(Long id) {
        super("Book", id);
    }
}
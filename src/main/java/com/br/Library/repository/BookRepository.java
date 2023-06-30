package com.br.Library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.Library.model.BookModel;

public interface BookRepository extends JpaRepository<BookModel, Long> {
    
}

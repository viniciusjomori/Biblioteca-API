package com.br.Library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.Library.enums.LoanStatus;
import com.br.Library.model.BookModel;
import com.br.Library.model.LoanModel;
import com.br.Library.model.UserModel;

public interface LoanRepository extends JpaRepository<LoanModel, Long> {
    Iterable<LoanModel> findAllByBook(BookModel book);
    Iterable<LoanModel> findAllByClient(UserModel client);
    Iterable<LoanModel> findAllByStatus(LoanStatus status);
}

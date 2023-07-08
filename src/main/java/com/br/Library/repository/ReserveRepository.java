package com.br.Library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.Library.enums.ReserveStatus;
import com.br.Library.model.BookModel;
import com.br.Library.model.ReserveModel;
import com.br.Library.model.UserModel;

public interface ReserveRepository extends JpaRepository<ReserveModel, Long> {
    Iterable<ReserveModel> findAllByBook(BookModel book);
    Iterable<ReserveModel> findAllByClient(UserModel client);
    Iterable<ReserveModel> findAllByStatus(ReserveStatus status);

}

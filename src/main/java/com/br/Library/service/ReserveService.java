package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.Library.enums.ReserveStatus;
import com.br.Library.exceptions.ResponseStatusException;
import com.br.Library.model.BookModel;
import com.br.Library.model.LoanModel;
import com.br.Library.model.ReserveModel;
import com.br.Library.model.UserModel;
import com.br.Library.repository.ReserveRepository;

@Service
public class ReserveService {
    
    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private BookService bookService;

    @Autowired
    private LoanService loanService;

    public Iterable<ReserveModel> getAll() {
        return reserveRepository.findAll();
    }

    public ReserveModel createReserve(long bookId, String tokenJwt) {
        UserModel client = clientService.findOnlineClient(tokenJwt);
        BookModel book = bookService.findById(bookId);
        if(book.getAvailableCopies() == 0) {
            throw new ResponseStatusException(
                "Unavailable book", 
                HttpStatus.CONFLICT
            );
        }
        ReserveModel reserve = new ReserveModel();
        reserve.setClient(client);
        reserve.setBook(book);
        reserve.setExpirationDate(reserve.getReserveDate().plusDays(7));
        reserve.getBook().setAvailableCopies(
            reserve.getBook().getAvailableCopies() -1
        );
        return reserveRepository.save(reserve);
    }

    public ReserveModel cancel(long id, String tokenJwt) {
        UserModel client = clientService.findOnlineClient(tokenJwt);
        ReserveModel reserve = findById(id);
        if(reserve.getClient().equals(client)) {
            reserve.setStatus(ReserveStatus.CANCELED);
            reserve.getBook().setAvailableCopies(
                reserve.getBook().getAvailableCopies() +1
            );
            return reserveRepository.save(reserve);
        } else {
            throw new ResponseStatusException(
                "Unathorized", 
                HttpStatus.FORBIDDEN
            );
        }
        
    }

    public ReserveModel findById(long id) {
        Optional<ReserveModel> optional = reserveRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(
                "Reserve not found", 
                HttpStatus.NOT_FOUND
            );
        }
    }

    public Iterable<ReserveModel> findAllByBook(long bookId) {
        BookModel book = bookService.findById(bookId);
        return reserveRepository.findAllByBook(book);
    }

    public Iterable<ReserveModel> findAllByClient(long clientId) {
        UserModel client = clientService.findById(clientId);
        return reserveRepository.findAllByClient(client);
    }

    public LoanModel createLoanFromReserve(long reserveId) {
        ReserveModel reserve = findById(reserveId);
        if(reserve.getStatus() == ReserveStatus.ACTIVE) {
            reserve.getBook().setAvailableCopies(
            reserve.getBook().getAvailableCopies() +1
            );
            reserve.setStatus(ReserveStatus.DONE);
            reserveRepository.save(reserve);
            return loanService.createLoan(
                reserve.getClient().getId(),
                reserve.getBook().getId()
            );
        } else {
            throw new ResponseStatusException(
            "The reserve is not active", 
                HttpStatus.CONFLICT
            );
        }
        
    }
}
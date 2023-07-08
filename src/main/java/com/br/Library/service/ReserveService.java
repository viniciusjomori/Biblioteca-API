package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.enums.ReserveStatus;
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

    public ReserveModel createReserve(long bookId) {
        UserModel client = clientService.getAuthenticatedClient();
        BookModel book = bookService.findById(bookId);
        if(book.getAvailableCopies() > 0) {
            ReserveModel reserve = new ReserveModel();
            reserve.setClient(client);
            reserve.setBook(book);
            reserve.setExpirationDate(reserve.getReserveDate().plusDays(7));
            reserve.getBook().setAvailableCopies(
                reserve.getBook().getAvailableCopies() -1
            );
            return reserveRepository.save(reserve);
        } else {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Unavailable book"
            );
        }
        
    }

    public ReserveModel cancel(long id) {
        UserModel client = clientService.getAuthenticatedClient();
        ReserveModel reserve = findById(id);
        if(reserve.getClient().getId() != client.getId()) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "Unathorized"
            );
        } else if(reserve.getStatus() != ReserveStatus.ACTIVE) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Reserve is not active"
            );
        }
        
        reserve.setStatus(ReserveStatus.CANCELED);
        reserve.getBook().setAvailableCopies(
            reserve.getBook().getAvailableCopies() +1
        );
        return reserveRepository.save(reserve);
        
    }

    public ReserveModel findById(long id) {
        Optional<ReserveModel> optional = reserveRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Reserve not found"
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
                HttpStatus.CONFLICT,
                "The reserve is not active"
            );
        }
        
    }
}
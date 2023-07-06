package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.br.Library.dto.LoanRequestDTO;
import com.br.Library.exceptions.ResponseStatusException;
import com.br.Library.model.BookModel;
import com.br.Library.model.LoanModel;
import com.br.Library.model.UserModel;
import com.br.Library.repository.LoanRepository;

@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientService clientService;
    
    @Autowired
    private BookService bookService;    

    public Iterable<LoanModel> getAll() {
        return loanRepository.findAll();
    }

    public LoanModel createLoan(LoanRequestDTO dto) {
        return createLoan(
            dto.clientId(),
            dto.bookId()
        );
    }

    public LoanModel createLoan(long clientId, long bookId) {
        UserModel client = clientService.findById(clientId);
        BookModel book = bookService.findById(bookId);
        if(book.getAvailableCopies() == 0) {
            throw new ResponseStatusException(
                "Unavailable book", 
                HttpStatus.CONFLICT
            );
        }
        LoanModel loan = new LoanModel();
        loan.setClient(client);
        loan.setBook(book);
        loan.setDeliveryDate(loan.getLoanDate().plusDays(30));
        loan.getBook().setAvailableCopies(
            loan.getBook().getAvailableCopies() -1
        );
        return loanRepository.save(loan);
    }

    public LoanModel deliver(long id) {
        LoanModel loan = findById(id);
        loan.setActive(false);
        loan.getBook().setAvailableCopies(
            loan.getBook().getAvailableCopies() +1
        );
        return loanRepository.save(loan);
    }

    public LoanModel findById(long id) {
        Optional<LoanModel> optional = loanRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(
                "Loan not found", 
                HttpStatus.NOT_FOUND
            );
        }
    }

    public Iterable<LoanModel> findAllByBook(long bookId) {
        BookModel book = bookService.findById(bookId);
        return loanRepository.findAllByBook(book);
    }

    public Iterable<LoanModel> findAllByClient(long clientId) {
        UserModel client = clientService.findById(clientId);
        return loanRepository.findAllByClient(client);
    }
}

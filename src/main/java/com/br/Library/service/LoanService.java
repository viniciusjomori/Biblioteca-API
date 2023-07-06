package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.Library.dto.LoanRequestDTO;
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
        UserModel client = clientService.findById(dto.clientId());
        BookModel book = bookService.findById(dto.bookId());
        if(book.getAvailableCopies() == 0) {
            throw new RuntimeException("unavailable book");
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
            throw new RuntimeException("loan not found");
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

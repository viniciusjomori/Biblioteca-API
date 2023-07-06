package com.br.Library.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.Library.enums.ReserveStatus;
import com.br.Library.model.BookModel;
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

    public Iterable<ReserveModel> getAll() {
        return reserveRepository.findAll();
    }

    public ReserveModel createReserve(long bookId, String tokenJwt) {
        UserModel client = clientService.findOnlineClient(tokenJwt);
        BookModel book = bookService.findById(bookId);
        if(book.getAvailableCopies() == 0) {
            throw new RuntimeException("unavailable book");
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
            throw new RuntimeException("sem autorização");
        }
        
    }

    public ReserveModel findById(long id) {
        Optional<ReserveModel> optional = reserveRepository.findById(id);
        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Reserve not found");
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
}
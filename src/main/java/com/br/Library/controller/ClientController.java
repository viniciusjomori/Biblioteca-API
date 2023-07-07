package com.br.Library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.Library.dto.ClientResponseDTO;
import com.br.Library.dto.ReserveResponseDTO;
import com.br.Library.dto.UserRequestDTO;
import com.br.Library.mapper.ClientMapper;
import com.br.Library.mapper.ReserveMapper;
import com.br.Library.model.LoanModel;
import com.br.Library.model.ReserveModel;
import com.br.Library.model.UserModel;
import com.br.Library.service.ClientService;
import com.br.Library.service.LoanService;
import com.br.Library.service.ReserveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
@CrossOrigin(origins = "*")
public class ClientController {
    
    @Autowired
    private ClientService clientService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ReserveService reserveService;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ReserveMapper reserveMapper;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody @Valid UserRequestDTO requestDTO) {
        UserModel client = clientService.createClient(requestDTO);
        return ResponseEntity.ok(clientMapper.toResponseDTO(client));
    }

    @GetMapping("/online")
    public ResponseEntity<ClientResponseDTO> findOnlineClient() {
        UserModel client = clientService.getAuthenticatedClient();
        Iterable<LoanModel> loans = loanService.findAllByClient(client.getId());
        Iterable<ReserveModel> reserves = reserveService.findAllByClient(client.getId());
        ClientResponseDTO dto = clientMapper.toResponseDTO(client, loans, reserves);
        return ResponseEntity.ok(dto);
    } 

    @PostMapping("reserve/{bookId}")
    public ResponseEntity<ReserveResponseDTO> createReserve(@PathVariable long bookId) {
        ReserveModel model = reserveService.createReserve(bookId);
        return ResponseEntity.ok(reserveMapper.toResponseDTO(model));
    }

    @PutMapping("/reserve/cancel/{id}")
    public ResponseEntity<ReserveResponseDTO> cancel(@PathVariable long id) {
        ReserveModel reserve = reserveService.cancel(id);
        return ResponseEntity.ok(reserveMapper.toResponseDTO(reserve));
    }

}

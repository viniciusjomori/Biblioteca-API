package com.br.Library.dto;

import java.time.LocalDate;

import com.br.Library.enums.LoanStatus;

public record LoanResponseDTO(
    long id,
    BookInfo book,
    UserInfo client,
    LocalDate loanDate,
    LocalDate deliveryDate,
    LoanStatus status
) {}

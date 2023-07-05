package com.br.Library.dto;

import java.time.LocalDate;

public record LoanResponseDTO(
    long id,
    BookInfo book,
    UserInfo client,
    LocalDate loanDate,
    LocalDate deliveryDate,
    boolean active
) {}

package com.br.Library.dto;

import java.time.LocalDate;

public record LoanResponseDTO(
    long id,
    BookResponseDTO book,
    UserResponseDTO client,
    LocalDate loanDate,
    LocalDate deliveryDate,
    boolean active
) {}

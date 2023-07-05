package com.br.Library.dto;

import java.time.LocalDate;

public record LoanInfo(
    long id,
    LocalDate loanDate,
    LocalDate deliveryDate,
    boolean active
) {}

package com.br.Library.dto;

import java.time.LocalDate;

import com.br.Library.enums.LoanStatus;

public record LoanInfo(
    long id,
    LocalDate loanDate,
    LocalDate deliveryDate,
    LoanStatus status
) {}

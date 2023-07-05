package com.br.Library.dto;

import jakarta.validation.constraints.NotNull;

public record LoanRequestDTO(
    @NotNull long clientId,
    @NotNull long bookId
) {}

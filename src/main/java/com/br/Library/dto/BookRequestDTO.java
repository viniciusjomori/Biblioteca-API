package com.br.Library.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequestDTO(
    @NotBlank String title,
    @NotBlank String author,
    @NotBlank String publishingCompany,
    @NotNull int totalCopies,
    @NotNull LocalDate releaseDate
) {}

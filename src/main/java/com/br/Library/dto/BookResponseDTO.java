package com.br.Library.dto;

import java.time.LocalDate;

public record BookResponseDTO(
    long id,
    String title,
    String author,
    String publishingCompany,
    LocalDate releaseDate,
    int totalCopies,
    int availableCopies,
    Iterable<LoanInfo> loans,
    Iterable<ReserveInfo> reserves
) {
    
}

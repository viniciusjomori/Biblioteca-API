package com.br.Library.dto;

import java.time.LocalDate;

public record BookInfo(
    long id,
    String title,
    String author,
    String publishingCompany,
    LocalDate releaseDate,
    int totalCopies,
    int availableCopies
) {}

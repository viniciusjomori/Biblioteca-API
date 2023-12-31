package com.br.Library.dto;

import java.time.LocalDate;

import com.br.Library.enums.ReserveStatus;

public record ReserveResponseDTO(
    long id,
    BookInfo book,
    UserInfo client,
    LocalDate reserveDate,
    LocalDate expirationDate,
    ReserveStatus status
) {
    
}

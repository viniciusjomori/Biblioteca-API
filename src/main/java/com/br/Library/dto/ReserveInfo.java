package com.br.Library.dto;

import java.time.LocalDate;

import com.br.Library.enums.ReserveStatus;

public record ReserveInfo(
    long id,
    LocalDate reserveDate,
    LocalDate expirationDate,
    ReserveStatus status
) {}

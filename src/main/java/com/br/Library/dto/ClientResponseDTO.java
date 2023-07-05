package com.br.Library.dto;

public record ClientResponseDTO(
    long id,
    String username,
    boolean accountNonExpired,
    boolean accountNonLocked,
    boolean credentialsNonExpired,
    boolean enabled,
    Iterable<LoanInfo> loans,
    Iterable<ReserveInfo> reserves
) {
}

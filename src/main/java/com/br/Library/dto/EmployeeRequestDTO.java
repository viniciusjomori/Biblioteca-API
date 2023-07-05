package com.br.Library.dto;

import jakarta.validation.constraints.NotBlank;

public record EmployeeRequestDTO(
    @NotBlank String username,
    @NotBlank String password,
    @NotBlank String role
) {
    
}

package com.br.Library.dto;

import jakarta.validation.constraints.NotBlank;

public record EmployeeRequestDTO(
    @NotBlank String username,
    @NotBlank String password,
    @NotBlank String role

) {
    public EmployeeRequestDTO {
        role = role.toUpperCase();
        if(!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
    }
}

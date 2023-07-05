package com.br.Library.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
    @NotBlank String username,
    @NotBlank String password
) {}

package com.br.Library.dto;

import java.util.Collection;

import com.br.Library.enums.RoleName;

import jakarta.validation.constraints.NotNull;

public record RoleResponseDTO(
    @NotNull Long id,
    @NotNull RoleName name,
    @NotNull Collection<UserResponseDTO> users
    ) {}

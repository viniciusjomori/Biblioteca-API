package com.br.Library.dto;

import com.br.Library.enums.RoleName;

import jakarta.validation.constraints.NotNull;

public record RoleResponseDTO(
    @NotNull Long id,
    @NotNull RoleName name,
    @NotNull Iterable<UserInfo> users
    ) {}

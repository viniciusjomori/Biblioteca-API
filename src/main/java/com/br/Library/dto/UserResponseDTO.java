package com.br.Library.dto;

import com.br.Library.enums.RoleName;

public record UserResponseDTO(
    long id,
    String username,
    RoleName role,
    boolean accountNonExpired,
    boolean accountNonLocked,
    boolean credentialsNonExpired,
    boolean enabled
) {}

package com.br.Library.dto;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.br.Library.enums.RoleName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Component
@Data
public class EmployeeRequestDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotNull
    private RoleName role;

    public void setRole(String string) {
        string = string.toUpperCase();
        if(!string.startsWith("ROLE_")) {
            string = "ROLE_"+string;
        }
        try {
            this.role = RoleName.valueOf(string);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Role not found"
            );
        }
    }

}

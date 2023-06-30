package com.br.Library.exceptions.abstracts;

import org.springframework.http.HttpStatus;

public abstract class ResourceNotFoundException extends SuperException {
    public ResourceNotFoundException(String entity, long id) {
        super (
            entity + " " + id + " not found",
            HttpStatus.NOT_FOUND
        );
    }
}

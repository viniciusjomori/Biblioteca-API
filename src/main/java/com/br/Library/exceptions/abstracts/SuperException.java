package com.br.Library.exceptions.abstracts;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class SuperException extends RuntimeException {
    public HttpStatus httpStatus;

    public SuperException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
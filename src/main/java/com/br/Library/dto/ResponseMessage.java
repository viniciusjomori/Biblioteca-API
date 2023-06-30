package com.br.Library.dto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class ResponseMessage {
    private String message;
    private HttpStatus httpStatus;
}

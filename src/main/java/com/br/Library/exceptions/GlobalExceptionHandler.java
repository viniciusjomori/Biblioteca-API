package com.br.Library.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.br.Library.dto.ResponseMessage;
import com.br.Library.exceptions.abstracts.SuperException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ResponseMessage responseMessage;
    
    @ExceptionHandler(SuperException.class)
    public ResponseEntity<ResponseMessage> handleSuperException(SuperException ex) {
        responseMessage.setMessage(ex.getMessage());
        responseMessage.setHttpStatus(ex.getHttpStatus());
        return new ResponseEntity<ResponseMessage>(responseMessage, ex.getHttpStatus());
    }

}
package com.br.Library.exceptions.user;

import com.br.Library.exceptions.abstracts.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    
    public UserNotFoundException(Long id) {
        super("Book", id);
    }
}

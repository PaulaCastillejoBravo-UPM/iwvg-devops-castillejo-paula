package es.upm.miw.devops.es.upm.api.services.exceptions;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID id) {
        super("User with ID " + id + " not found");
    }
}